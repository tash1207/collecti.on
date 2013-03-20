package collecti.on;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import collecti.on.adapters.BrowseCollectionsAdapter;
import collecti.on.dataypes.Collection;
import collecti.on.dataypes.User;
import collecti.on.db.DatabaseHelper;
import collecti.on.misc.Utility;

public class UserProfile extends Activity {
	String user_id;
	User user;
	final static int UPLOAD_PIC = 11;
	final static int AFTER_CROP = 22;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		
		SharedPreferences prefs = getSharedPreferences("Collection", Context.MODE_PRIVATE);
		user_id = prefs.getString("user_id", "");
		user = DatabaseHelper.getHelper(this).getUser(user_id);
		
		ImageView profile_pic = (ImageView) findViewById(R.id.profile_picture);
		if (!user.photo.equals("")) {
			profile_pic.setImageBitmap(Utility.getBitmapFromString(user.photo));
		}
		
		ListView my_collections = (ListView) findViewById(R.id.lvw_my_collections);
		List<Collection> list = DatabaseHelper.getHelper(this).getCollectionsByUser(user_id);
		my_collections.setAdapter(new BrowseCollectionsAdapter(this, R.layout.custom_lvw_collections, 
				R.id.collection_title, list, true));
		
		TextView username = (TextView) findViewById(R.id.profile_username);
		username.setText(user.username);
		TextView number_of_collections = (TextView) findViewById(R.id.profile_collections_count);
		number_of_collections.setText(String.valueOf(list.size()));
	}
	
	public void upload_profile_picture(View v) {
		Intent uploadPic = new Intent(Intent.ACTION_PICK, 
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(Intent.createChooser(uploadPic, "Upload photo using:"), UPLOAD_PIC);
	}
	
	public void add_collection(View v) {
		Intent add_collection = new Intent(getApplicationContext(), AddCollection.class);
		startActivity(add_collection);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == UPLOAD_PIC && data.getData() != null) {
				Uri selectedImage = data.getData();
    			Utility.doCrop(this, selectedImage, 100, 100, 1, 1, AFTER_CROP);
			}
			else if (requestCode == AFTER_CROP) {
				Bundle extras = data.getExtras();
				if (extras != null) {					
					Bitmap uploaded_photo = extras.getParcelable("data");
					user.photo = Base64.encodeToString(Utility.getBitmapAsByteArray(uploaded_photo), 
							Base64.DEFAULT);
					DatabaseHelper.getHelper(this).updateUser(user);
					
					ImageView picture = (ImageView) findViewById(R.id.profile_picture);
    				picture.setImageBitmap(uploaded_photo);
				}
    		}
		}
	}
}
