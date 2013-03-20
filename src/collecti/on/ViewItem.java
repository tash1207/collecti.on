package collecti.on;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import collecti.on.dataypes.Item;
import collecti.on.db.DatabaseHelper;
import collecti.on.misc.Utility;

public class ViewItem extends Activity {
	String item_id;
	Item item;
	
	// OnActivity Result Variables
	final static int UPLOAD_PIC = 11;
	final static int AFTER_CROP = 22;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		boolean my_collection = getIntent().getBooleanExtra("my_collection", false);
		item_id = getIntent().getStringExtra("item_id");
		item = DatabaseHelper.getHelper(this).getItem(item_id);
		
		if (my_collection) {
			setContentView(R.layout.activity_edit_item);
			
			ImageView picture = (ImageView) findViewById(R.id.edit_item_picture);
			EditText title = (EditText) findViewById(R.id.edit_item_title);
			EditText description = (EditText) findViewById(R.id.edit_item_description);
			
			picture.setImageBitmap(Utility.getBitmapFromString(item.picture));
			title.setText(item.title);
			description.setText(item.description);
		}
		else {
			setContentView(R.layout.activity_view_item);
			
			ImageView picture = (ImageView) findViewById(R.id.view_item_picture);
			TextView title = (TextView) findViewById(R.id.view_item_title);
			TextView description = (TextView) findViewById(R.id.view_item_description);
			
			picture.setImageBitmap(Utility.getBitmapFromString(item.picture));
			title.setText(item.title);
			description.setText(item.description);
			
			if (item.title.equals("")) title.setVisibility(View.GONE);
			if (item.description.equals("")) description.setVisibility(View.GONE);
		}
	}
	
	public void shade_clicked(View v) {
		finish();
	}
	
	public void change_picture(View v) {
		Intent uploadPic = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(Intent.createChooser(uploadPic, "Upload photo using:"), UPLOAD_PIC);
	}
	
	public void save_changes(View v) {
		EditText title = (EditText) findViewById(R.id.edit_item_title);
		EditText description = (EditText) findViewById(R.id.edit_item_description);
		
		item.title = title.getText().toString();
		item.description = description.getText().toString();
		DatabaseHelper.getHelper(this).insertItem(item);
		
		finish();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == UPLOAD_PIC && data.getData() != null) {
    			Uri selectedImage = data.getData();
    			Utility.doCrop(this, selectedImage, 200, 200, 1, 1, AFTER_CROP);
    		}
    		else if (requestCode == AFTER_CROP) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					Bitmap uploaded_photo = extras.getParcelable("data");
					ImageView picture = (ImageView) findViewById(R.id.edit_item_picture);
    				picture.setImageBitmap(uploaded_photo);
    				item.picture = Base64.encodeToString(Utility.getBitmapAsByteArray(uploaded_photo), Base64.DEFAULT);
				}
    		}
		}
	}
}
