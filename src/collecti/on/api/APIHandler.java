package collecti.on.api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class APIHandler {
	public static Activity activity;
	
	public static void init(Activity activity) {
		APIHandler.activity = activity;
	}

	// User Functions
	
	public static void setUserId(String user_id) {
		SharedPreferences prefs = activity.getSharedPreferences("Collection", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("user_id", user_id);
		editor.commit();
	}
	
	public static String getUserId() {
		String user_id = activity.getSharedPreferences("Collection", Context.MODE_PRIVATE).getString("user_id", "");
		return user_id;
	}
	
	public static void login(String username, String password) {
		UserAPI.login(activity, username, password);
	}
	
}
