package collecti.on.collection;

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
import collecti.on.misc.LoadImageCache;
import collecti.on.misc.Utility;

import com.slidingmenu.lib.SlidingMenu;

public class BrowseCollections extends Activity {
	SharedPreferences prefs;
	SlidingMenu menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_collections);
		
		prefs = getSharedPreferences("Collection", Context.MODE_PRIVATE);
		String username = prefs.getString("username", "");
		if (!username.equals("")) {
			ImageView login = (ImageView) findViewById(R.id.header_login_button);
			ImageView user = (ImageView) findViewById(R.id.header_user_button);
			
			login.setVisibility(View.GONE);
			user.setVisibility(View.VISIBLE);
			LoadImageCache loader = new LoadImageCache(this);
			loader.display(prefs.getString("photo", ""), user);
		}
		
		Collection snowglobes = new Collection("123", "Dave", "Snowglobes", "Christmas!", "Figurines", false, 
				"http://www.ddetc.com/313-large/disney-vintage-christmas-snowglobe-music-box.jpg");
		Collection stamps = new Collection("124", "Chris Dolphin", "Stamps", "WWII Era", "Stamps", false, 
				"http://www.scarceantiqueshop.com/antique_stamp_523x600.jpg");
		ArrayList<Collection> list = new ArrayList<Collection>();
		list.add(snowglobes);
		list.add(stamps);
		ListView collections = (ListView) findViewById(R.id.lvw_collections);
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
		String username = prefs.getString("username", "");
		if (!username.equals("")) {
			Intent add_collection = new Intent(getApplicationContext(), AddCollection.class);
			startActivity(add_collection);
		}
		
		else {
			Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void header_search_clicked(View v) {
		
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
		Intent signup = new Intent(getApplicationContext(), Signup.class);
		signup.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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
