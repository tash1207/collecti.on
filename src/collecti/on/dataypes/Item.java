package collecti.on.dataypes;

import android.content.ContentValues;
import android.database.Cursor;

public class Item {
	public String collection_id;
	public String id;
	public String title;
	public String description;
	public String picture;
	
	public Item(String collection_id, String title, String description, String picture) {
		this.collection_id = collection_id;
		this.title = title;
		this.description = description;
		this.picture = picture;
	}
	
	public Item(Cursor item) {
	  	id = item.getString(item.getColumnIndex("item_id"));
	  	collection_id = item.getString(item.getColumnIndex("collection_id"));
	  	title = item.getString(item.getColumnIndex("item_title"));
	  	description = item.getString(item.getColumnIndex("item_description"));
	  	picture = item.getString(item.getColumnIndex("item_picture"));
	}
	
	public ContentValues toContentValues() {
	  	ContentValues row = new ContentValues();
	  	row.put("item_id", id);
	  	row.put("collection_id", collection_id);
	  	row.put("item_title", title);
	  	row.put("item_description", description);
	  	row.put("item_picture", picture);
	  	return row;
	}

}
