package collecti.on.collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
	}
	
	public void signup_clicked(View v) {
		EditText username_edit = (EditText) findViewById(R.id.signup_username);
		EditText email_edit = (EditText) findViewById(R.id.signup_email);
		EditText password_edit = (EditText) findViewById(R.id.signup_password);
		
		String username = username_edit.getText().toString();
		String email = email_edit.getText().toString();
		String password = password_edit.getText().toString();
		
		if (username.equals("") || email.equals("") || password.equals("")) {
			Toast.makeText(this, "Please enter text into all fields", Toast.LENGTH_SHORT).show();
		}
		else if (!email.contains("@") && !email.contains(".")) {
			Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
		}
		else {
			// signup process and then enter the app
			Intent browse = new Intent(getApplicationContext(), BrowseCollections.class);
			startActivity(browse);
			finish();
		}

	}

}
