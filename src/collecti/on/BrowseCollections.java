package collecti.on;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import collecti.on.adapters.BrowseCollectionsAdapter;
import collecti.on.adapters.SideMenuAdapter;
import collecti.on.dataypes.Collection;
import collecti.on.dataypes.User;
import collecti.on.db.DatabaseHelper;
import collecti.on.misc.Utility;

import com.slidingmenu.lib.SlidingMenu;

public class BrowseCollections extends Activity {
	SharedPreferences prefs;
	String user_id;
	User user;
	public ListView collections;
	public SlidingMenu menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_collections);
		
		prefs = getSharedPreferences("Collection", Context.MODE_PRIVATE);
		user_id = prefs.getString("user_id", "");
		user = DatabaseHelper.getHelper(this).getUser(user_id);
		
		ArrayList<Collection> list = DatabaseHelper.getHelper(this).getAllCollections();
		collections = (ListView) findViewById(R.id.lvw_collections);
		collections.setAdapter(new BrowseCollectionsAdapter(this, R.layout.custom_lvw_collections, 
				R.id.collection_title, list));
		
		// Side Menu Setup
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.RIGHT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setBehindWidth(Utility.getScreenWidth(this) - Utility.getPixels(this, 55));
		menu.setBehindScrollScale(1.0f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		menu.setMenu(R.layout.menu_browse_collections);
		
		ListView browse_collections = (ListView) findViewById(R.id.lvw_browse_collections);
		browse_collections.setAdapter(new SideMenuAdapter(this, R.layout.custom_lvw_side_menu, R.id.side_menu_category,
				getResources().getStringArray(R.array.collection_categories)));
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		if (!user_id.equals("")) {
			ImageView login = (ImageView) findViewById(R.id.header_login_button);
			ImageView user_photo = (ImageView) findViewById(R.id.header_user_button);
			
			login.setVisibility(View.GONE);
			user_photo.setVisibility(View.VISIBLE);
			if (!user.photo.equals("")) {
				user_photo.setImageBitmap(Utility.getBitmapFromString(user.photo));
			}
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
		RelativeLayout login_menu = (RelativeLayout) findViewById(R.id.login_menu);
		RelativeLayout user_menu = (RelativeLayout) findViewById(R.id.user_menu);
		
		login_menu.setVisibility(View.GONE);
		user_menu.setVisibility(View.GONE);
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
	
	public void header_person_clicked(View v) {
		// display the user menu
		RelativeLayout user_menu = (RelativeLayout) findViewById(R.id.user_menu);
		if (user_menu.getVisibility() == View.GONE) {
			user_menu.setVisibility(View.VISIBLE);
		}
		else {
			user_menu.setVisibility(View.GONE);
		}
	}

	public void header_add_clicked(View v) {
		String user_id = prefs.getString("user_id", "");
		if (!user_id.equals("")) {
			Intent add_collection = new Intent(getApplicationContext(), AddCollection.class);
			startActivity(add_collection);
		}
		
		else {
			Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void header_search_clicked(View v) {
		Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
	}

	public void header_side_menu_clicked(View v) {
		menu.showMenu();
	}
	
	// LOGIN MENU BUTTONS
	
	public void login_clicked(View v) {
		Intent login = new Intent(getApplicationContext(), Login.class);
		login.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(login);
	}
	
	public void signup_clicked(View v) {
		Intent signup = new Intent(getApplicationContext(), Login.class);
		signup.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		signup.putExtra("signup", true);
		startActivity(signup);
	}
	
	// USER MENU BUTTONS
	
	public void view_profile_clicked(View v) {
		Intent user_profile = new Intent(getApplicationContext(), UserProfile.class);
		startActivity(user_profile);
	}
	
	public void logout_clicked(View v) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.clear();
		editor.commit();
		
		Intent login = new Intent(getApplicationContext(), Login.class);
		login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(login);
		finish();
	}

}
