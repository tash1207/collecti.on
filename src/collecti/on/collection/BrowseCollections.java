package collecti.on.collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import collecti.on.misc.Utility;

import com.slidingmenu.lib.SlidingMenu;

public class BrowseCollections extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_collections);
		
		// Side Menu Setup
		
		SlidingMenu menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.RIGHT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setBehindWidth(Utility.getScreenWidth(this) - Utility.getPixels(this, 45));
		menu.setBehindScrollScale(1.0f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		menu.setMenu(R.layout.activity_signup);
	}
	
	// HEADER BUTTONS
	
	public void header_login_clicked(View v) {
		// display the login menu
		RelativeLayout login_menu = (RelativeLayout) findViewById(R.id.login_menu);
		if (login_menu.getVisibility() == View.GONE) {
			login_menu.setVisibility(View.VISIBLE);
		}
		else {
			login_menu.setVisibility(View.GONE);
		}
	}
	
	public void header_search_clicked(View v) {
		
	}

	public void header_add_clicked(View v) {
	
	}

	public void header_side_menu_clicked(View v) {
	
	}
	
	// LOGIN MENU BUTTONS
	
	public void login_clicked(View v) {
		Intent login = new Intent(getApplicationContext(), Login.class);
		startActivity(login);
	}
	
	public void signup_clicked(View v) {
		Intent signup = new Intent(getApplicationContext(), Signup.class);
		startActivity(signup);
	}

}
