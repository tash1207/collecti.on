package collecti.on.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import collecti.on.BrowseCollections;
import collecti.on.R;
import collecti.on.dataypes.Collection;
import collecti.on.db.DatabaseHelper;

public class SideMenuAdapter extends ArrayAdapter<String> {
	Context context;
	int layout;
	String[] categories;

	public SideMenuAdapter(Context context, int layout, int textView, String[] categories) {
		super(context, layout, textView, categories);
		this.context = context;
		this.layout = layout;
		this.categories = categories;
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(layout, parent, false);
		
		TypedArray icons = getContext().getResources().obtainTypedArray(R.array.collection_category_icons);
		ImageView image = (ImageView) convertView.findViewById(R.id.side_menu_icon);
		image.setImageResource(icons.getResourceId(position, 0));
		icons.recycle();
		
		TextView text = (TextView) convertView.findViewById(R.id.side_menu_category);
		text.setText(categories[position]);
		
		OnClickListener listener = new OnClickListener() {
	    	
			public void onClick(View v) {
				// filter by category for collections list view
				ListView lvw = ((BrowseCollections) context).collections;
				if (position == 0) {
					ArrayList<Collection> collections = DatabaseHelper.getHelper(context).
							getAllCollections();
					((BrowseCollectionsAdapter) lvw.getAdapter()).updateList(collections);
				}
				else {
					ArrayList<Collection> collections = DatabaseHelper.getHelper(context).
							getAllCollectionsByCategory(categories[position]);
					((BrowseCollectionsAdapter) lvw.getAdapter()).updateList(collections);
				}
				((BrowseCollections) context).menu.showContent();
			}
	    };
		
	    convertView.setOnClickListener(listener);
		
		return convertView;
	}

}
