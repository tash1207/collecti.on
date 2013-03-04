package collecti.on.collection;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// if user is already logged in, open browse collections activity
	}
	
	public void login_clicked(View v) {
		// what happens after logging in
		EditText username_edit = (EditText) findViewById(R.id.login_username);
		EditText password_edit = (EditText) findViewById(R.id.login_password);
		
		String username = username_edit.getText().toString();
		String password = password_edit.getText().toString();

		RequestParams requestParams = new RequestParams();
		requestParams.put("username", username);
		requestParams.put("password", password);

		AsyncHttpRequest.POST("/user/login", requestParams, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				Log.d("json", response.toString());
				
				try {
					if ((Boolean) response.get("login")) {
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
