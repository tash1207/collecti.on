package collecti.on;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import collecti.on.misc.Utility;

public class ViewCollection extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_collection);
		
		ImageView item1 = (ImageView) findViewById(R.id.item1);
		ImageView item2 = (ImageView) findViewById(R.id.item2);
		ImageView item3 = (ImageView) findViewById(R.id.item3);
		ImageView item4 = (ImageView) findViewById(R.id.item4);
		ImageView item5 = (ImageView) findViewById(R.id.item5);
		ImageView item6 = (ImageView) findViewById(R.id.item6);
		
		int screenWidth = Utility.getScreenWidth(this);
		item1.getLayoutParams().width = screenWidth / 3;
		item1.getLayoutParams().height = screenWidth / 3;
		item2.getLayoutParams().width = screenWidth / 3;
		item2.getLayoutParams().height = screenWidth / 3;
		item3.getLayoutParams().width = screenWidth / 3;
		item3.getLayoutParams().height = screenWidth / 3;
		item4.getLayoutParams().width = screenWidth / 3;
		item4.getLayoutParams().height = screenWidth / 3;
		item5.getLayoutParams().width = screenWidth / 3;
		item5.getLayoutParams().height = screenWidth / 3;
		item6.getLayoutParams().width = screenWidth / 3;
		item6.getLayoutParams().height = screenWidth / 3;
		
	}

}
