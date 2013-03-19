package collecti.on.dataypes;

import android.content.ContentValues;
import android.database.Cursor;

public class User {
	public String id;
	public String username;
	public String email;
	public String photo;
	
	public User(String username, String email, String photo) {
		this.username = username;
		this.email = email;
		this.photo = photo;
	}
	
	public User(Cursor user) {
	  	id = user.getString(user.getColumnIndex("user_id"));
	  	username = user.getString(user.getColumnIndex("user_name"));
	  	email = user.getString(user.getColumnIndex("user_email"));
	  	photo = user.getString(user.getColumnIndex("user_picture"));
	}
	
	public ContentValues toContentValues() {
	  	ContentValues row = new ContentValues();
	  	row.put("user_id", id);
	  	row.put("user_name", username);
	  	row.put("user_email", email);
	  	row.put("user_picture", photo);
	  	return row;
	}

}
