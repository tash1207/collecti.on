package collecti.on;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import collecti.on.adapters.ViewCollectionAdapter;
import collecti.on.dataypes.Collection;
import collecti.on.dataypes.Item;
import collecti.on.dataypes.User;
import collecti.on.db.DatabaseHelper;
import collecti.on.misc.Utility;

public class ViewCollection extends Activity {
	String collection_id;
	Collection collection;
	
	boolean my_collection = false;
	
	// OnActivity Result Variables
	final static int UPLOAD_PIC_ITEM = 11;
	final static int AFTER_CROP_ITEM = 22;
	final static int UPLOAD_PIC_COLLECTION = 33;
	final static int AFTER_CROP_COLLECTION = 44;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_collection);
		
		SharedPreferences prefs = getSharedPreferences("Collection", Context.MODE_PRIVATE);
		String user_id = prefs.getString("user_id", "");
		
		collection_id = getIntent().getStringExtra("collection_id");
		collection = DatabaseHelper.getHelper(this).getCollection(collection_id);
		
		ImageView image = (ImageView) findViewById(R.id.collection_picture);
		if (!collection.photo.equals("")) image.setImageBitmap(Utility.getBitmapFromString(collection.photo));
		else image.setImageResource(Utility.getDefaultCollectionImage(collection));
		
		TextView title = (TextView) findViewById(R.id.collection_title);
		title.setText(collection.title);

		TextView username = (TextView) findViewById(R.id.collection_username);
		User user = DatabaseHelper.getHelper(this).getUser(collection.user_id);
		if (user != null) username.setText("by " + user.username);
		
		TextView description = (TextView) findViewById(R.id.collection_description);
		if (collection.description.equals("")) description.setVisibility(View.GONE);
		description.setText(collection.description);
		
		if (user_id.equals(collection.user_id)) my_collection = true;

		// Edit-Mode of View Collection
		if (my_collection) {
			LinearLayout add_item_button = (LinearLayout) findViewById(R.id.add_item_button);
			add_item_button.setVisibility(View.VISIBLE);
			
			image.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent uploadPic = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(Intent.createChooser(uploadPic, "Upload photo using:"), UPLOAD_PIC_COLLECTION);
				}
			});
		}
		
		updateGridView();
	}
	
	public void updateGridView() {
		ArrayList<Item> items = DatabaseHelper.getHelper(this).getItemsByCollection(collection_id);
		GridView gridView = (GridView) findViewById(R.id.gridView);
		gridView.setAdapter(new ViewCollectionAdapter(this, R.layout.custom_gridview_items, 
				R.id.gridview_item_picture, items, my_collection));
	}
	
	public void add_item(View v) {
		Intent uploadPic = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(Intent.createChooser(uploadPic, "Upload photo using:"), UPLOAD_PIC_ITEM);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == UPLOAD_PIC_ITEM && data.getData() != null) {
    			Uri selectedImage = data.getData();
    			Utility.doCrop(this, selectedImage, 200, 200, 1, 1, AFTER_CROP_ITEM);
    		}
    		else if (requestCode == AFTER_CROP_ITEM) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					Bitmap uploaded_photo = extras.getParcelable("data");
    				String item_picture = Base64.encodeToString(
    						Utility.getBitmapAsByteArray(uploaded_photo), Base64.DEFAULT);

    				Item item = new Item(collection_id, "", "", item_picture);
    				DatabaseHelper.getHelper(this).insertItem(item);
    				
    				updateGridView();
				}
    		}
    		else if (requestCode == UPLOAD_PIC_COLLECTION && data.getData() != null) {
    			Uri selectedImage = data.getData();
    			Utility.doCrop(this, selectedImage, 150, 150, 1, 1, AFTER_CROP_COLLECTION);
    		}
    		else if (requestCode == AFTER_CROP_COLLECTION) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					Bitmap uploaded_photo = extras.getParcelable("data");
					collection.photo = Base64.encodeToString(
							Utility.getBitmapAsByteArray(uploaded_photo), Base64.DEFAULT);
    				DatabaseHelper.getHelper(this).insertCollection(collection);
    				
    				ImageView image = (ImageView) findViewById(R.id.collection_picture);
    				image.setImageBitmap(Utility.getBitmapFromString(collection.photo));
				}
    		}
		}
	}

}
