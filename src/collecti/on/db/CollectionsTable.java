package collecti.on.db;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import collecti.on.dataypes.Collection;

public class CollectionsTable {

	private static final String TABLE_NAME = "collections";
	private static final String USER_ID = "user_id";
	private static final String COLLECTION_ID = "collection_id";
	private static final String COLLECTION_TITLE = "collection_title";
	private static final String COLLECTION_DESCRIPTION = "collection_description";
	private static final String COLLECTION_CATEGORY = "collection_category";
	private static final String COLLECTION_PRIVATE = "collection_private";
	private static final String COLLECTION_PICTURE = "collection_picture";

	public static void createTable(SQLiteDatabase db) {
		String CREATE_COLLECTIONS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + USER_ID + 
				" INTEGER, " +	COLLECTION_ID + " TEXT PRIMARY KEY, " + COLLECTION_TITLE + 
				" TEXT, " + COLLECTION_DESCRIPTION + " TEXT, " + COLLECTION_CATEGORY + " TEXT, " + 
				COLLECTION_PRIVATE + " BOOLEAN, " + COLLECTION_PICTURE + " TEXT)";
		db.execSQL(CREATE_COLLECTIONS_TABLE);
	}
	
	public static void dropTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}
	
	public static long insert(Collection collection, SQLiteDatabase db) {
		return db.replace(TABLE_NAME, null, collection.toContentValues());
	}
	
	public static Collection get(String id, SQLiteDatabase db) {
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE collection_id = '" + id + "';", null);
		Collection collection = null;
		
		if ( cursor.moveToFirst() ) {
			collection = new Collection(cursor);
		}
		cursor.close();
		return collection;
	}
	
	public static void delete(Collection collection, SQLiteDatabase db) {
    	String id = collection.id;
    	db.delete(TABLE_NAME, "collection_id='" + id + "'", null);
	}
	
	public static ArrayList<Collection> getAllByUser(String user_id, SQLiteDatabase db) {
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE user_id = '" + user_id + "'", null);
		ArrayList<Collection> collections = new ArrayList<Collection>();
		
		for (boolean hasItem = cursor.moveToFirst(); hasItem; hasItem = cursor.moveToNext()) {
			collections.add(new Collection(cursor));
		}
		cursor.close();
		
		return collections;
	}
	
	public static ArrayList<Collection> getAll(SQLiteDatabase db) {
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		ArrayList<Collection> collections = new ArrayList<Collection>();
		
		for (boolean hasItem = cursor.moveToFirst(); hasItem; hasItem = cursor.moveToNext()) {
			if (cursor.getString(cursor.getColumnIndex(COLLECTION_PRIVATE)).equals("0")) {
				collections.add(new Collection(cursor));
			}
		}
		cursor.close();
		
		return collections;
	}
	
	public static ArrayList<Collection> getAllByCategory(String category, SQLiteDatabase db) {
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE collection_category = '" + category + "';", null);
		ArrayList<Collection> collections = new ArrayList<Collection>();
		
		for (boolean hasItem = cursor.moveToFirst(); hasItem; hasItem = cursor.moveToNext()) {
			if (cursor.getString(cursor.getColumnIndex(COLLECTION_PRIVATE)).equals("0")) {
				collections.add(new Collection(cursor));
			}
		}
		cursor.close();
		
		return collections;
	}
}
