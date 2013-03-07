package collecti.on.collection;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import collecti.on.http.AsyncHttpRequest;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Login extends Activity {
	SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		prefs = getSharedPreferences("Collection", Context.MODE_PRIVATE);
		/*
		SharedPreferences.Editor editor = prefs.edit();
		editor.clear();
		editor.commit();
		*/
		String username = prefs.getString("username", "");
		if (!username.equals("")) {
			Intent browse = new Intent(getApplicationContext(), BrowseCollections.class);
			startActivity(browse);
			finish();
		}
		
		// if user is already logged in, open browse collections activity
	}
	
	public void login_clicked(View v) {
		// what happens after logging in
		EditText username_edit = (EditText) findViewById(R.id.login_username);
		EditText password_edit = (EditText) findViewById(R.id.login_password);
		
		String username = username_edit.getText().toString();
		String password = password_edit.getText().toString();
		
		if (username.equals("") || password.equals("")) {
			Toast.makeText(this, "Please enter a username and a password", Toast.LENGTH_SHORT).show();
			return;
		}

		RequestParams requestParams = new RequestParams();
		requestParams.put("username", username);
		requestParams.put("password", password);

		AsyncHttpRequest.POST("/user/login", requestParams, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				Log.d("json", response.toString());
				
				try {
					if ((Boolean) response.get("login")) {
						JSONObject user = response.getJSONObject("user");
						SharedPreferences.Editor editor = prefs.edit();
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
			    
			    Toast.makeText(Login.this, "Please enter a valid username/password combo", 
			    		Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public void signup_clicked(View v) {
		Intent signup = new Intent(getApplicationContext(), Signup.class);
		startActivity(signup);
	}
	
	public void browse_clicked(View v) {
		Intent browse = new Intent(getApplicationContext(), BrowseCollections.class);
		startActivity(browse);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
