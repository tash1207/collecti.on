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
import collecti.on.api.APIHandler;
import collecti.on.dataypes.User;
import collecti.on.db.DatabaseHelper;

public class Login extends Activity {
	SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		prefs = getSharedPreferences("Collection", Context.MODE_PRIVATE);
		
		// if user is already logged in, open browse collections activity
		APIHandler.init(this);
		String user_id = APIHandler.getUserId();
		if (!user_id.equals("")) {
			Intent browse = new Intent(getApplicationContext(), BrowseCollections.class);
			startActivity(browse);
			finish();
		}
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
		EditText password_edit = (EditText) findViewById(R.id.login_password);
		
		String username = username_edit.getText().toString();
		String password = password_edit.getText().toString();
		
		// Server-Side Login
		APIHandler.init(this);
		APIHandler.login(username, password);
	}
	
	public void login_success(String username, boolean successful) {
		// If post request went through
		if (successful) {
			if (APIHandler.getUserId().equals("")) {
				Toast.makeText(this, "invalid username/password combo", Toast.LENGTH_SHORT).show();
			}
			else {
				Intent browse = new Intent(getApplicationContext(), BrowseCollections.class);
				startActivity(browse);
				finish();
			}
		}
		// Otherwise try client-side login
		else {
			String user_id = DatabaseHelper.getHelper(this).userLogin(username);
			if (user_id.equals("")) {
				Toast.makeText(Login.this, "Please enter a valid username", Toast.LENGTH_SHORT).show();
			}
			else {
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString("user_id", user_id);
				editor.commit();
				
				Intent browse = new Intent(getApplicationContext(), BrowseCollections.class);
				startActivity(browse);
				finish();
			}
		}
	}
	
	public void signup_clicked(View v) {
		EditText username_edit = (EditText) findViewById(R.id.signup_username);
		EditText email_edit = (EditText) findViewById(R.id.signup_email);
		EditText password_edit = (EditText) findViewById(R.id.signup_password);
		
		String username = username_edit.getText().toString();
		String email = email_edit.getText().toString();
		String password = password_edit.getText().toString();
		
		// Server-Side signup
		APIHandler.init(this);
		APIHandler.signup(username, email, password);
	}
	
	public void signup_success(String user_id, String username, String email) {
		// Save user client-side
		User user = new User(user_id, username, email, "");
		DatabaseHelper.getHelper(this).createUser(user);
		
		Log.d("user", user_id);
		Log.d("user", APIHandler.getUserId());
		
		user = DatabaseHelper.getHelper(this).getUser(APIHandler.getUserId());
		Log.d("user", user.id);
		Log.d("user", user.username);
		Log.d("user", user.photo);
		
		// Go into application
		Intent browse = new Intent(getApplicationContext(), BrowseCollections.class);
		startActivity(browse);
		finish();
	}
	
	public void bottom_signup_clicked(View v) {
		final RelativeLayout login_layout = (RelativeLayout) findViewById(R.id.login_layout);
		final RelativeLayout signup_layout = (RelativeLayout) findViewById(R.id.signup_layout);
		
		login_layout.setVisibility(View.GONE);
		Animation fade_in = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in);
		signup_layout.startAnimation(fade_in);
		signup_layout.setVisibility(View.VISIBLE);
	}
	
	public void bottom_login_clicked(View v) {
		final RelativeLayout login_layout = (RelativeLayout) findViewById(R.id.login_layout);
		final RelativeLayout signup_layout = (RelativeLayout) findViewById(R.id.signup_layout);

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
