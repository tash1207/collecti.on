package collecti.on.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import collecti.on.R;

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
		
		TextView text = (TextView) convertView.findViewById(R.id.side_menu_category);
		text.setText(categories[position]);
		
		OnClickListener listener = new OnClickListener() {
	    	
			public void onClick(View v) {
				// update data for collections list view
			}
	    };
		
	    convertView.setOnClickListener(listener);
		
		return convertView;
	}

}
