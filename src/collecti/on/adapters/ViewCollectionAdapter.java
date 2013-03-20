package collecti.on.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import collecti.on.R;
import collecti.on.ViewItem;
import collecti.on.dataypes.Item;
import collecti.on.db.DatabaseHelper;
import collecti.on.misc.Utility;

public class ViewCollectionAdapter extends ArrayAdapter<Item> {
	Context context;
	int layout;
	List<Item> items;
	boolean my_collection;

	public ViewCollectionAdapter(Context context, int layout, int view, List<Item> items) {
		super(context, layout, view, items);
		this.context = context;
		this.layout = layout;
		this.items = items;
		this.my_collection = false;
	}
	
	public ViewCollectionAdapter(Context context, int layout, int view, List<Item> items, boolean my_collection) {
		super(context, layout, view, items);
		this.context = context;
		this.layout = layout;
		this.items = items;
		this.my_collection = my_collection;
	}
	
	public void updateList(ArrayList<Item> items) {
		this.items.clear();
		this.items.addAll(items);
		this.notifyDataSetChanged();
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(layout, parent, false);
		
		ImageView image = (ImageView) convertView.findViewById(R.id.gridview_item_picture);
		image.setImageBitmap(Utility.getBitmapFromString(items.get(position).picture));
		
		OnClickListener listener = new OnClickListener() {
	    	
			public void onClick(View v) {
				// open that collection!
				Intent view_item = new Intent(context, ViewItem.class);
				view_item.putExtra("item_id", items.get(position).id);
				view_item.putExtra("my_collection", my_collection);
				context.startActivity(view_item);
			}
	    };
	    
	    OnLongClickListener long_click_listener = new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				String collection_id = items.get(position).collection_id;
				DatabaseHelper.getHelper(context).deleteItem(items.get(position).id);
				updateList(DatabaseHelper.getHelper(context).getItemsByCollection(collection_id));
				return true;
			}
		};
		
	    convertView.setOnClickListener(listener);
	    if (my_collection) convertView.setOnLongClickListener(long_click_listener);
		
		return convertView;
	}

}
