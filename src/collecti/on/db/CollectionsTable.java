package collecti.on.db;

import android.database.sqlite.SQLiteDatabase;

public class CollectionsTable {

	private static final String TABLE_NAME = "collections";
	private static final String COLLECTION_ID = "collection_id";
	private static final String COLLECTION_TITLE = "collection_title";
	private static final String COLLECTION_DESCRIPTION = "collection_description";
	private static final String COLLECTION_PICTURE = "collection_picture";

	public static void createTable(SQLiteDatabase db) {
		String CREATE_COLLECTIONS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + 
				COLLECTION_ID + " INTEGER NOT NULL PRIMARY KEY, "+ COLLECTION_TITLE + " TEXT, " + 
				COLLECTION_DESCRIPTION + " TEXT, " + COLLECTION_PICTURE + " TEXT)";
		db.execSQL(CREATE_COLLECTIONS_TABLE);
	}
}
