package collecti.on.dataypes;

import collecti.on.misc.BetterBoolean;
import android.content.ContentValues;
import android.database.Cursor;

public class Collection {
	public String id;
	public String user_id;
	public String title;
	public String description;
	public String category;
	public Boolean is_private;
	public String photo;
	
	public Long[] itemIDs;
	
	public Collection (String user_id, String title, String description, 
			String category, Boolean is_private, String photo) {
		this.user_id = user_id;
		this.title= title;
		this.description = description;
		this.category = category;
		this.is_private = is_private;
		this.photo = photo;
	}
	
	public Collection(Cursor collection) {
	  	id = collection.getString(collection.getColumnIndex("collection_id"));
	  	user_id = collection.getString(collection.getColumnIndex("user_id"));
	  	title = collection.getString(collection.getColumnIndex("collection_title"));
	  	description = collection.getString(collection.getColumnIndex("collection_description"));
	  	category = collection.getString(collection.getColumnIndex("collection_category"));
	  	is_private = new BetterBoolean(collection.getString(collection.getColumnIndex("collection_private"))).toBoolean();
	  	photo = collection.getString(collection.getColumnIndex("collection_picture"));
	}
	
	public ContentValues toContentValues() {
	  	ContentValues row = new ContentValues();
	  	row.put("collection_id", id);
	  	row.put("user_id", user_id);
	  	row.put("collection_title", title);
	  	row.put("collection_description", description);
	  	row.put("collection_category", category);
	  	row.put("collection_private", is_private);
	  	row.put("collection_picture", photo);
	  	return row;
	}

}
