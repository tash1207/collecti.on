package collecti.on.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import collecti.on.R;
import collecti.on.ViewCollection;
import collecti.on.dataypes.Collection;
import collecti.on.misc.LoadImageCache;

public class BrowseCollectionsAdapter extends ArrayAdapter<Collection> {
	Context context;
	int layout;
	List<Collection> collections;
	LoadImageCache loader;
	boolean my_collection;

	public BrowseCollectionsAdapter(Context context, int layout, int textView, 
			List<Collection> collections) {
		super(context, layout, textView, collections);

		this.context = context;
		this.layout = layout;
		this.collections = collections;
		this.loader = new LoadImageCache(this.context);
		this.my_collection = false;
	}
	
	public BrowseCollectionsAdapter(Context context, int layout, int textView, 
			List<Collection> collections, boolean my_collection) {
		super(context, layout, textView, collections);

		this.context = context;
		this.layout = layout;
		this.collections = collections;
		this.loader = new LoadImageCache(this.context);
		this.my_collection = my_collection;
	}

	static class ViewHolder {
		protected ImageView image;
		protected TextView title;
		protected TextView username;
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = new ViewHolder();
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(layout, parent, false);
		
		viewHolder.image = (ImageView) convertView.findViewById(R.id.collection_image);
		viewHolder.title = (TextView) convertView.findViewById(R.id.collection_title);
		viewHolder.username = (TextView) convertView.findViewById(R.id.collection_username);
		
		if (my_collection) {
			viewHolder.title.setTextSize(20);
			viewHolder.username.setVisibility(View.GONE);
		}		
		
		// Set the default image ..
		if (collections.get(position).category.equals("Books")) {
			viewHolder.image.setImageResource(R.drawable.stock_book_icon);
		}
		else if (collections.get(position).category.equals("Cards")) {
			viewHolder.image.setImageResource(R.drawable.stock_card_icon);
		}
		else if (collections.get(position).category.equals("Coins")) {
			viewHolder.image.setImageResource(R.drawable.stock_coin_icon);
		}
		else if (collections.get(position).category.equals("Electronics")) {
			viewHolder.image.setImageResource(R.drawable.stock_electronic_icon);
		}
		else if (collections.get(position).category.equals("Figurines")) {
			viewHolder.image.setImageResource(R.drawable.stock_figurine_icon);
		}
		else if (collections.get(position).category.equals("Media")) {
			viewHolder.image.setImageResource(R.drawable.stock_media_icon);
		}
		else if (collections.get(position).category.equals("Stamps")) {
			viewHolder.image.setImageResource(R.drawable.stock_stamp_icon);
		}
		
		// .. and attempt to load the real one
		loader.display(collections.get(position).photo, viewHolder.image);
		
		viewHolder.title.setText(collections.get(position).title);
		viewHolder.username.setText("by " + collections.get(position).username);
		
		OnClickListener listener = new OnClickListener() {
	    	
			public void onClick(View v) {
				// open that collection!
				Intent view_collection = new Intent(context, ViewCollection.class);
				context.startActivity(view_collection);
			}
	    };
		
	    convertView.setOnClickListener(listener);
		
		return convertView;
	}

}
