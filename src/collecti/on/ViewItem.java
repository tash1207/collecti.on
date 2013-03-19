package collecti.on;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import collecti.on.dataypes.Item;
import collecti.on.db.DatabaseHelper;
import collecti.on.misc.Utility;

public class ViewItem extends Activity {
	String item_id;
	Item item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		boolean my_collection = getIntent().getBooleanExtra("my_collection", false);
		item_id = getIntent().getStringExtra("item_id");
		item = DatabaseHelper.getHelper(this).getItem(item_id);
		
		if (my_collection) {
			setContentView(R.layout.activity_edit_item);
			
			ImageView picture = (ImageView) findViewById(R.id.edit_item_picture);
			EditText title = (EditText) findViewById(R.id.edit_item_title);
			EditText description = (EditText) findViewById(R.id.edit_item_description);
			
			picture.setImageBitmap(Utility.getBitmapFromString(item.picture));
			title.setText(item.title);
			description.setText(item.description);
		}
		else {
			setContentView(R.layout.activity_view_item);
			
			ImageView picture = (ImageView) findViewById(R.id.view_item_picture);
			TextView title = (TextView) findViewById(R.id.view_item_title);
			TextView description = (TextView) findViewById(R.id.view_item_description);
			
			picture.setImageBitmap(Utility.getBitmapFromString(item.picture));
			title.setText(item.title);
			description.setText(item.description);
		}
	}
	
	public void shade_clicked(View v) {
		finish();
	}
	
	public void change_picture(View v) {
		
	}
	
	public void save_changes(View v) {
		EditText title = (EditText) findViewById(R.id.edit_item_title);
		EditText description = (EditText) findViewById(R.id.edit_item_description);
		
		item.title = title.getText().toString();
		item.description = description.getText().toString();
		DatabaseHelper.getHelper(this).insertItem(item);
		
		finish();
	}
}
