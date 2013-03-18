package collecti.on.db;

import android.database.sqlite.SQLiteDatabase;

public class UserTable {
	private static final String TABLE_NAME = "users";
	private static final String USER_ID = "user_id";
	private static final String USER_NAME = "user_name";
	private static final String USER_EMAIL = "user_email";
	private static final String USER_PASSWORD = "user_password";

	public static void createTable(SQLiteDatabase db) {
		String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + 
				USER_ID + " INTEGER NOT NULL PRIMARY KEY, "+ USER_NAME + " TEXT, " + USER_EMAIL + 
				" TEXT, " + USER_PASSWORD + " TEXT)";
		db.execSQL(CREATE_USERS_TABLE);
	}

}
