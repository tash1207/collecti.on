package collecti.on;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import collecti.on.adapters.ViewCollectionAdapter;
import collecti.on.dataypes.Collection;
import collecti.on.dataypes.Item;
import collecti.on.db.DatabaseHelper;

public class ViewCollection extends Activity {
	String collection_id;
	Collection collection;
	
	boolean my_collection = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_collection);
		
		SharedPreferences prefs = getSharedPreferences("Collection", Context.MODE_PRIVATE);
		String user_id = prefs.getString("user_id", "");
		
		collection_id = getIntent().getStringExtra("collection_id");
		collection = DatabaseHelper.getHelper(this).getCollection(collection_id);
		
		if (user_id.equals(collection.user_id)) my_collection = true;

		if (my_collection) {
			LinearLayout add_item_button = (LinearLayout) findViewById(R.id.add_item_button);
			add_item_button.setVisibility(View.VISIBLE);
		}
				
		ArrayList<Item> items = DatabaseHelper.getHelper(this).getItemsByCollection(collection_id);
		GridView gridView = (GridView) findViewById(R.id.gridView);
		gridView.setAdapter(new ViewCollectionAdapter(this, R.layout.custom_gridview_items, 
				R.id.gridview_item_picture, items, my_collection));
	}
	
	public void add_item(View v) {
		
	}

}
