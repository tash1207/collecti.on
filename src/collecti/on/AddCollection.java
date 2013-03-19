package collecti.on;

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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import collecti.on.dataypes.Collection;
import collecti.on.db.DatabaseHelper;
import collecti.on.http.AsyncHttpRequest;
import collecti.on.misc.Utility;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AddCollection extends Activity {
	SharedPreferences prefs;
	String user_id;
	long item_id = 0;
	String data_url = "";
	
	// OnActivity Result Variables
	final static int UPLOAD_PIC = 11;
	final static int AFTER_CROP = 22;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_collection);
		
		prefs = getSharedPreferences("Collection", Context.MODE_PRIVATE);
		user_id = prefs.getString("user_id", "");
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
		Intent uploadPic = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(Intent.createChooser(uploadPic, "Upload photo using:"), UPLOAD_PIC);
	}
	
	public void create_collection(View v) {
		// upload item first and get item-id
		//item_id = send_items_to_server();
		
		EditText title_edit = (EditText) findViewById(R.id.edit_title);
		EditText description_edit = (EditText) findViewById(R.id.edit_description);
		TextView chosen_category = (TextView) findViewById(R.id.chosen_category);
		CheckBox privacy = (CheckBox) findViewById(R.id.cbx_privacy);
		
		String title = title_edit.getText().toString();
		String description = description_edit.getText().toString();
		String category = chosen_category.getText().toString();
		Boolean is_private = privacy.isChecked();
		
		Collection collection = new Collection(user_id, title, description, category, is_private, "");
		DatabaseHelper.getHelper(this).insertCollection(collection);
		
		/*
		RequestParams requestParams = new RequestParams();
		requestParams.put("username", "marco");
		requestParams.put("img-url",  data_url);
		requestParams.put("collection-title", title);
		requestParams.put("collection-description", description);
		requestParams.put("category", category);
		requestParams.put("is-private", is_private.toString());
		requestParams.put("item-ids", "[" + String.valueOf(item_id) + "]");
		
		AsyncHttpRequest.POST("/user/createCollection", requestParams, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				Log.d("json_create_collection", response.toString());
				
				try {
					response.get("collection");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
			    Log.d("json_create_collection", errorResponse.toString());
			    
			    Toast.makeText(AddCollection.this, "An error occurred", Toast.LENGTH_SHORT).show();
			}
		});
		*/
		
		Intent user_profile = new Intent(getApplicationContext(), UserProfile.class);
		startActivity(user_profile);
		finish();
	}
	
	public long send_items_to_server() {
		Log.d("add_collection", "sending items to server");
		EditText title_edit = (EditText) findViewById(R.id.item_title);
		EditText description_edit = (EditText) findViewById(R.id.item_description);
		
		String title = title_edit.getText().toString();
		String description = description_edit.getText().toString();
		
		if (title.equals("") || data_url.equals("")) {
		    Toast.makeText(this, "Please enter required information", Toast.LENGTH_SHORT).show();
		    return 0;
		}
				
		RequestParams requestParams = new RequestParams();
		requestParams.put("user_id", user_id);
		requestParams.put("item-title", title);
		requestParams.put("item-description", description);
		requestParams.put("item-img-url", data_url);
		
		AsyncHttpRequest.POST("/user/addItem", requestParams, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				Log.d("json_add_item", response.toString());
				try {
					if (response.getBoolean("success")) {
						item_id = response.getLong("id");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
			    Log.d("json_add_item", errorResponse.toString());
			    Toast.makeText(AddCollection.this, "An error occurred", Toast.LENGTH_SHORT).show();
			}
		});
		
		return item_id;
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
					LinearLayout add_item_button = (LinearLayout) findViewById(R.id.add_item_button);
					add_item_button.setVisibility(View.GONE);
					RelativeLayout item = (RelativeLayout) findViewById(R.id.item);
					item.setVisibility(View.VISIBLE);
					ImageView picture = (ImageView) findViewById(R.id.item_picture);
    				picture.setImageBitmap(uploaded_photo);
    				data_url = Base64.encodeToString(Utility.getBitmapAsByteArray(uploaded_photo), Base64.DEFAULT);
				}
    		}
		}
	}

}
