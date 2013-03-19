package collecti.on.db;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import collecti.on.dataypes.Item;

public class ItemsTable {

	private static final String TABLE_NAME = "items";
	private static final String COLLECTION_ID = "collection_id";
	private static final String ITEM_ID = "item_id";
	private static final String ITEM_TITLE = "item_title";
	private static final String ITEM_DESCRIPTION = "item_description";
	private static final String ITEM_PICTURE = "item_picture";

	public static void createTable(SQLiteDatabase db) {
		String CREATE_ITEMS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + COLLECTION_ID + 
				" INTEGER, " + ITEM_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+ ITEM_TITLE + " TEXT, " + 
				ITEM_DESCRIPTION + " TEXT, " + ITEM_PICTURE + " TEXT)";
		db.execSQL(CREATE_ITEMS_TABLE);
	}
	
	public static void dropTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}
	
	public static void insert(Item item, SQLiteDatabase db) {
		db.replace(TABLE_NAME, null, item.toContentValues());
	}
	
	public static Item get(String item_id, SQLiteDatabase db) {
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE item_id='" + item_id + "';", null);
		Item item = null;
		
		if ( cursor.moveToFirst() ) {
			item = new Item(cursor);
		}
		cursor.close();
		return item;
	}
	
	public static void delete(Item item, SQLiteDatabase db) {
    	String id = item.id;
    	db.delete(TABLE_NAME, "item_id='" + id + "'", null);
	}
	
	public static ArrayList<Item> getAllByCollection(String collection_id, SQLiteDatabase db) {
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE collection_id='" + collection_id + "'", null);
		ArrayList<Item> items = new ArrayList<Item>();
		
		for (boolean hasItem = cursor.moveToFirst(); hasItem; hasItem = cursor.moveToNext()) {
			items.add(new Item(cursor));
		}
		cursor.close();
		
		return items;
	}
	
}
