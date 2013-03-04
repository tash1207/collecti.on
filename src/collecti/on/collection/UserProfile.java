package collecti.on.collection;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import collecti.on.adapters.BrowseCollectionsAdapter;
import collecti.on.dataypes.Collection;

public class UserProfile extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		
		ListView my_collections = (ListView) findViewById(R.id.lvw_my_collections);
		Collection snowglobes = new Collection("Dave", "Snowglobes", "My Collection!", "http://www.ddetc.com/313-large/disney-vintage-christmas-snowglobe-music-box.jpg");
		Collection stamps = new Collection("Chris", "Stamps", "WWII Era", "http://www.scarceantiqueshop.com/antique_stamp_523x600.jpg");
		ArrayList<Collection> list = new ArrayList<Collection>();
		list.add(snowglobes);
		list.add(stamps);
		my_collections.setAdapter(new BrowseCollectionsAdapter(this, R.layout.custom_lvw_collections, 
				R.id.collection_title, list, true));
	}
	
	public void add_collection(View v) {
		
	}

}
