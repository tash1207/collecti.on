package collecti.on.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import collecti.on.dataypes.User;

public class UserTable {
	private static final String TABLE_NAME = "users";
	private static final String USER_ID = "user_id";
	private static final String USER_NAME = "user_name";
	private static final String USER_EMAIL = "user_email";
	private static final String USER_PICTURE = "user_picture";

	public static void createTable(SQLiteDatabase db) {
		String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + 
				USER_ID + " TEXT PRIMARY KEY, " + USER_NAME + " TEXT, " + USER_EMAIL + 
				" TEXT, " + USER_PICTURE + " TEXT)";
		db.execSQL(CREATE_USERS_TABLE);
	}
	
	public static void dropTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}
	
	public static String login(String user_name, SQLiteDatabase db) {		
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE user_name=?", new String[] {user_name});
		String user_id = "";

		if ( cursor.moveToFirst() ) {
			user_id = cursor.getString(cursor.getColumnIndex(USER_ID));
		}
		cursor.close();
		
		return user_id;
	}
	
	public static void create(User user, SQLiteDatabase db) {
		db.insert(TABLE_NAME, null, user.toContentValues());
		Log.d("user cv", user.toContentValues().toString());
	}
	
	public static User get(String user_id, SQLiteDatabase db) {
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE user_id=?", new String[] { user_id });
		User user = null;
		Log.d("user", "querying");
		
		if ( cursor.moveToFirst() ) {
			Log.d("user", "got a match!");
			user = new User(cursor);
			Log.d("user cv", user.toContentValues().toString());
		}
		cursor.close();
		return user;
	}
	
	public static void update(User user, SQLiteDatabase db) {
		db.replace(TABLE_NAME, null, user.toContentValues());
	}

}
