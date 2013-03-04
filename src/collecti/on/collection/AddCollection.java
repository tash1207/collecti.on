package collecti.on.collection;

import io.filepicker.FilePicker;
import io.filepicker.FilePickerAPI;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import collecti.on.http.AsyncHttpRequest;
import collecti.on.misc.Utility;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AddCollection extends Activity {
	SharedPreferences prefs;
	String username;
	
	// OnActivity Result Variables
	final static int UPLOAD_PIC = 11;
	final static int AFTER_CROP = 22;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_collection);
		
		prefs = getSharedPreferences("Collection", Context.MODE_PRIVATE);
		username = prefs.getString("username", "");
	}
	
	public void select_category(View v) {
		final TextView category = (TextView) findViewById(R.id.chosen_category);
		final String[] categories = getResources().getStringArray(R.array.collection_categories);
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle(R.string.category)
	           .setItems(R.array.collection_categories, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	            	    switch (which) {
						case 0:
							category.setText(categories[0]);
							break;
						case 1:
							category.setText(categories[1]);
							break;
						case 2:
							category.setText(categories[2]);
							break;
						case 3:
							category.setText(categories[3]);
							break;
						case 4:
							category.setText(categories[4]);
							break;
						case 5:
							category.setText(categories[5]);
							break;
						case 6:
							category.setText(categories[6]);
							break;
						default:
							break;
						}
	           }
	    });
	    AlertDialog dialog = builder.create();
	    dialog.show();
	}
	
	public void add_item(View v) {
		FilePickerAPI.setKey("AzI5rM78ISmyNrvJRkSpbz");
		
		Intent intent = new Intent(this, FilePicker.class);
		startActivityForResult(intent, FilePickerAPI.REQUEST_CODE_GETFILE);

		/*
		Intent uploadPic = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(Intent.createChooser(uploadPic, "Upload photo using:"), UPLOAD_PIC);
		*/
	}
	
	public void create_collection(View v) {
		// upload items first and get item-ids
		send_items_to_server();
		
		EditText title_edit = (EditText) findViewById(R.id.edit_title);
		EditText description_edit = (EditText) findViewById(R.id.edit_description);
		CheckBox privacy = (CheckBox) findViewById(R.id.cbx_privacy);
		
		String title = title_edit.getText().toString();
		String description = description_edit.getText().toString();
		Boolean is_private = privacy.isChecked();
		
		RequestParams requestParams = new RequestParams();
		requestParams.put("username", "marco");
		requestParams.put("collection-title", title);
		requestParams.put("collection-description", description);
		requestParams.put("is-private", is_private.toString());
		//requestParams.put("item-ids", ids) // List<IDs>
		
		AsyncHttpRequest.POST("/user/createCollection", requestParams, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				Log.d("json", response.toString());
				
				try {
					if ((Boolean) response.get("login")) {
						SharedPreferences.Editor editor = prefs.edit();
						JSONObject user = response.getJSONObject("user");
						editor.putString("username", user.getString("username"));
						editor.putString("email", user.getString("email"));
						editor.putString("photo", user.getString("url"));
						editor.commit();
						Intent browse = new Intent(getApplicationContext(), BrowseCollections.class);
						startActivity(browse);
						finish();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
			    Log.d("json", errorResponse.toString());
			    
			    Toast.makeText(AddCollection.this, "An error occurred", Toast.LENGTH_SHORT).show();
			}
		});
		
		Intent user_profile = new Intent(getApplicationContext(), UserProfile.class);
		startActivity(user_profile);
		finish();
	}
	
	public void send_items_to_server() {
		Log.d("add_collection", "sending items to server");
		EditText title_edit = (EditText) findViewById(R.id.item_title);
		EditText description_edit = (EditText) findViewById(R.id.item_description);
		
		String title = title_edit.getText().toString();
		String description = description_edit.getText().toString();
		
		RequestParams requestParams = new RequestParams();
		requestParams.put("username", "marco");
		requestParams.put("item-title", title);
		requestParams.put("item-description", description);
		requestParams.put("item-img-url", "http://www.ddetc.com/313-large/disney-vintage-christmas-snowglobe-music-box.jpg");
		
		AsyncHttpRequest.POST("/user/addItem", requestParams, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				Log.d("json", response.toString());
			}
			
			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
			    Log.d("json", errorResponse.toString());
			    Toast.makeText(AddCollection.this, "An error occurred", Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == FilePickerAPI.REQUEST_CODE_GETFILE) {
		        Uri uri = data.getData();
		        Utility.doCrop(this, uri, 100, 100, 1, 1, AFTER_CROP);
		        
		        Toast.makeText(this, "File path is " + uri.toString(), Toast.LENGTH_LONG).show();
		        Toast.makeText(this, "FPUrl: " + data.getExtras().getString("fpurl"), Toast.LENGTH_LONG).show();

		        System.out.println("File path is " + uri.toString());
		        System.out.println("FPUrl: " + data.getExtras().getString("fpurl"));
		    }
			else if (requestCode == UPLOAD_PIC && data.getData() != null) {
    			Uri selectedImage = data.getData();
    			Utility.doCrop(this, selectedImage, 100, 100, 1, 1, AFTER_CROP);
    		}
    		else if (requestCode == AFTER_CROP) {
				Bundle extras = data.getExtras();
				if (extras != null) {
					Bitmap uploaded_photo = extras.getParcelable("data");
					LinearLayout add_item_button = (LinearLayout) findViewById(R.id.add_item_button);
					add_item_button.setVisibility(View.GONE);
					RelativeLayout item = (RelativeLayout) findViewById(R.id.item);
					item.setVisibility(View.VISIBLE);
					ImageView picture = (ImageView) findViewById(R.id.item_picture);
    				picture.setImageBitmap(uploaded_photo);
				}
    		}
		}
	}

}
