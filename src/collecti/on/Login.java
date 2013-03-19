package collecti.on;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import collecti.on.dataypes.User;
import collecti.on.db.DatabaseHelper;

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
		String user_id = prefs.getString("user_id", "");
		if (!user_id.equals("")) {
			Intent browse = new Intent(getApplicationContext(), BrowseCollections.class);
			startActivity(browse);
			finish();
		}
		
		// if user is already logged in, open browse collections activity
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Boolean signup = getIntent().getBooleanExtra("signup", false);
		if (signup) {
			final RelativeLayout login_layout = (RelativeLayout) findViewById(R.id.login_layout);
			final RelativeLayout signup_layout = (RelativeLayout) findViewById(R.id.signup_layout);
			
			login_layout.setVisibility(View.GONE);
			signup_layout.setVisibility(View.VISIBLE);
		}
	}
	
	public void login_clicked(View v) {
		// what happens after logging in
		EditText username_edit = (EditText) findViewById(R.id.login_username);
		//EditText password_edit = (EditText) findViewById(R.id.login_password);
		
		String username = username_edit.getText().toString();
		//String password = password_edit.getText().toString();
		
		String user_id = DatabaseHelper.getHelper(this).userLogin(username);
		Log.d("user_id", user_id);
		if (user_id.equals("")) {
			Toast.makeText(Login.this, "Please enter a valid username/password combo", 
		    		Toast.LENGTH_SHORT).show();
		}
		else {
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("user_id", user_id);
			editor.commit();
			
			Intent browse = new Intent(getApplicationContext(), BrowseCollections.class);
			startActivity(browse);
			finish();
		}

		/*
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
		*/
	}
	
	public void signup_clicked(View v) {
		EditText username_edit = (EditText) findViewById(R.id.signup_username);
		EditText email_edit = (EditText) findViewById(R.id.signup_email);
		
		String username = username_edit.getText().toString();
		String email = email_edit.getText().toString();
		
		User user = new User(username, email, "");
		
		DatabaseHelper.getHelper(this).createUser(user);
		String user_id = DatabaseHelper.getHelper(this).userLogin(user.username);
		if (user_id.equals("")) {
			Toast.makeText(Login.this, "Error signing up. Please try again.", 
		    		Toast.LENGTH_SHORT).show();
		}
		else {
			Log.d("user_id", user_id);
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("user_id", user_id);
			editor.commit();
			
			Intent browse = new Intent(getApplicationContext(), BrowseCollections.class);
			startActivity(browse);
			finish();
		}
	}
	
	public void bottom_signup_clicked(View v) {
		final RelativeLayout login_layout = (RelativeLayout) findViewById(R.id.login_layout);
		final RelativeLayout signup_layout = (RelativeLayout) findViewById(R.id.signup_layout);
		
		//Animation fade_out = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
		//login_layout.startAnimation(fade_out);
		login_layout.setVisibility(View.GONE);
		Animation fade_in = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
		signup_layout.startAnimation(fade_in);
		signup_layout.setVisibility(View.VISIBLE);
	}
	
	public void bottom_login_clicked(View v) {
		final RelativeLayout login_layout = (RelativeLayout) findViewById(R.id.login_layout);
		final RelativeLayout signup_layout = (RelativeLayout) findViewById(R.id.signup_layout);
		
		//Animation fade_out = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out);
		//signup_layout.startAnimation(fade_out);
		signup_layout.setVisibility(View.GONE);
		Animation fade_in = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
		login_layout.startAnimation(fade_in);
		login_layout.setVisibility(View.VISIBLE);
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