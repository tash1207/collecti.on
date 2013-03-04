package collecti.on.collection;

import io.filepicker.FilePicker;
import io.filepicker.FilePickerAPI;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import collecti.on.adapters.BrowseCollectionsAdapter;
import collecti.on.dataypes.Collection;
import collecti.on.misc.LoadImageCache;
import collecti.on.misc.Utility;

public class UserProfile extends Activity {
	final static int AFTER_CROP = 22;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		
		SharedPreferences prefs = getSharedPreferences("Collection", Context.MODE_PRIVATE);
		ImageView profile_pic = (ImageView) findViewById(R.id.profile_picture);
		LoadImageCache loader = new LoadImageCache(this);
		loader.display(prefs.getString("photo", ""), profile_pic);
		
		ListView my_collections = (ListView) findViewById(R.id.lvw_my_collections);
		Collection snowglobes = new Collection("Dave", "Snowglobes", "My Collection!", "http://www.ddetc.com/313-large/disney-vintage-christmas-snowglobe-music-box.jpg");
		Collection stamps = new Collection("Chris", "Stamps", "WWII Era", "http://www.scarceantiqueshop.com/antique_stamp_523x600.jpg");
		ArrayList<Collection> list = new ArrayList<Collection>();
		list.add(snowglobes);
		list.add(stamps);
		my_collections.setAdapter(new BrowseCollectionsAdapter(this, R.layout.custom_lvw_collections, 
				R.id.collection_title, list, true));
	}
	
	public void upload_profile_picture(View v) {
		FilePickerAPI.setKey("AzI5rM78ISmyNrvJRkSpbz");
		
		Intent intent = new Intent(this, FilePicker.class);
		startActivityForResult(intent, FilePickerAPI.REQUEST_CODE_GETFILE);
	}
	
	public void add_collection(View v) {
		Intent add_collection = new Intent(getApplicationContext(), AddCollection.class);
		startActivity(add_collection);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == FilePickerAPI.REQUEST_CODE_GETFILE) {
		        Uri uri = data.getData();
		        Utility.doCrop(this, uri, 100, 100, 1, 1, AFTER_CROP);
		    }
    		else if (requestCode == AFTER_CROP) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					Bitmap uploaded_photo = extras.getParcelable("data");
					ImageView picture = (ImageView) findViewById(R.id.profile_picture);
    				picture.setImageBitmap(uploaded_photo);
				}
    		}
		}
	}

}
