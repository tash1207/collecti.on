package collecti.on.collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	
	public void log_in_clicked(View v) {
		// what happens after logging in
	}
	
	public void create_account_clicked(View v) {
		Intent signup = new Intent(getApplicationContext(), Signup.class);
		startActivity(signup);
	}
	
	public void browse_collections_clicked(View v) {
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
