package collecti.on.db;

import android.database.sqlite.SQLiteDatabase;

public class ItemsTable {

	private static final String TABLE_NAME = "items";
	private static final String ITEM_ID = "item_id";
	private static final String ITEM_TITLE = "item_title";
	private static final String ITEM_DESCRIPTION = "item_description";
	private static final String ITEM_PICTURE = "item_picture";

	public static void createTable(SQLiteDatabase db) {
		String CREATE_ITEMS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + 
				ITEM_ID + " INTEGER NOT NULL PRIMARY KEY, "+ ITEM_TITLE + " TEXT, " + 
				ITEM_DESCRIPTION + " TEXT, " + ITEM_PICTURE + " TEXT)";
		db.execSQL(CREATE_ITEMS_TABLE);
	}
	
}
