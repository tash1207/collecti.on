package collecti.on.db;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import collecti.on.dataypes.Collection;
import collecti.on.dataypes.Item;
import collecti.on.dataypes.User;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "cloudcollect";
	private static final int DATABASE_VERSION = 1;
	
	// Make sure there is only one helper and one database
	private static DatabaseHelper helper = null;
	private static SQLiteDatabase db = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		CollectionsTable.createTable(db);
		ItemsTable.createTable(db);
		UserTable.createTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		CollectionsTable.dropTable(db);
		ItemsTable.dropTable(db);
		UserTable.dropTable(db);
		
		onCreate(db);
	}
	
	public static synchronized DatabaseHelper getHelper(Context context) {
      if (helper == null) {
      	helper = new DatabaseHelper(context);
      	db = helper.getWritableDatabase();
      }
      return helper;
	}
	
	// Users methods
	
	public String userLogin(String user_name) {
		return UserTable.login(user_name, db);
	}
	
	public void createUser(User user) {
		UserTable.create(user, db);
	}
	
	public User getUser(String user_id) {
		return UserTable.get(user_id, db);
	}
	
	public void updateUser(User user) {
		UserTable.update(user, db);
	}
	
	// Collections CRUD methods
	/**
	 * Creates a new collection in the CollectionsTable
	 * @param collection
	 * @return the id of the newly-created collection
	 */
	public String insertCollection(Collection collection) {
		long id = CollectionsTable.insert(collection, db);
		return String.valueOf(id);
	}
	
	public Collection getCollection(String collection_id) {
	  	Collection collection = CollectionsTable.get(collection_id, db);
	  	return collection;
	}
	
	public void deleteCollection(Collection collection) {
		CollectionsTable.delete(collection, db);
	}
	
	public ArrayList<Collection> getCollectionsByUser(String user_id) {
		return CollectionsTable.getAllByUser(user_id, db);
	}
	
	public ArrayList<Collection> getAllCollections() {
		return CollectionsTable.getAll(db);
	}
	
	public ArrayList<Collection> getAllCollectionsByCategory(String category) {
		return CollectionsTable.getAllByCategory(category, db);
	}
	
	// Items CRUD methods
	
	public void insertItem(Item item) {
		ItemsTable.insert(item, db);
	}
	
	public Item getItem(String item_id) {
	  	Item item = ItemsTable.get(item_id, db);
	  	return item;
	}
	
	public void deleteItem(String item_id) {
		ItemsTable.delete(item_id, db);
	}
	
	public ArrayList<Item> getItemsByCollection(String collection_id) {
		return ItemsTable.getAllByCollection(collection_id, db);
	}
}
