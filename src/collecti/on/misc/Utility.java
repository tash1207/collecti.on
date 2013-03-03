package collecti.on.misc;

import java.io.ByteArrayOutputStream;
import java.util.List;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Utility {
	final static int EMAIL = 101;
	final static int PHONE = 102;
	final static int SMS = 103;
	final static int WEBSITE = 104;
	final static int BIO = 105;
	
	public Utility() {
		
	}

	public static Bitmap getRoundedCorners(Bitmap bitmap) {
    	Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
    	Canvas canvas = new Canvas(output);
    	final int color = 0xff424242;
    	final Paint paint = new Paint();
    	final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
    	final RectF rectF = new RectF(rect);
    	final float roundPx = 5;
    	
    	paint.setAntiAlias(true);
    	canvas.drawARGB(0, 0, 0, 0);
    	paint.setColor(color);
    	canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
    	
    	paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
    	canvas.drawBitmap(bitmap, rect, rect, paint);
    	
    	return output;
    }	
	
	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    	bitmap.compress(CompressFormat.PNG, 100, outputStream);
    	return outputStream.toByteArray();
    }
	
	public static Bitmap getBitmapFromString(String imgString) {
		byte[] b = Base64.decode(imgString, Base64.DEFAULT);
    	return BitmapFactory.decodeByteArray(b, 0, b.length);
	}
	
	public static int getPixels(Context context, float dips) {
		int pixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
	            dips, context.getResources().getDisplayMetrics());
		return pixels;
	}
	
	public static int getScreenWidth(Activity activity) {
		 DisplayMetrics metrics = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
			return metrics.widthPixels;
	}
	
	public static boolean doCrop(Context context, Uri mImageCaptureUri, int outputX, int outputY, int aspectX, 
			int aspectY, int requestCode) {
    	Intent intent = new Intent("com.android.camera.action.CROP");
    	intent.setType("image/*");
    	
    	List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, 0);
    	
    	int size = list.size();
    	
    	if (size == 0) {
    		Toast.makeText(context, "Cannot find photo cropper", Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	else {
    		intent.setData(mImageCaptureUri);
    		intent.putExtra("outputX", outputX);
    		intent.putExtra("outputY", outputY);
    		intent.putExtra("aspectX", aspectX);
    		intent.putExtra("aspectY", aspectY);
    		intent.putExtra("scale", true);
    		intent.putExtra("return-data", true);
    		
    		try {
				Intent i = new Intent(intent);
    			ResolveInfo res = list.get(0);
    			i.setComponent(new ComponentName(res.activityInfo.packageName, 
    					res.activityInfo.name));
    			((Activity) context).startActivityForResult(i, requestCode);
    			return true;
			} catch (Exception e) {
				Toast.makeText(context, "Problem with photo cropper", Toast.LENGTH_SHORT).show();
				return false;
			}
    	}
	}
	
	public static void showKeyboard(Activity activity, View view) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(
				Service.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, 0);
	}
	
	public static void hideKeyboard(Activity activity, View view) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(
				Service.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	
	public static int getListViewHeight(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		int totalHeight = 0;
        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
		return totalHeight;
	}
	
}
