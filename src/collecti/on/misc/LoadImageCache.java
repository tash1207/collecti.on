package collecti.on.misc;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

public class LoadImageCache {
	private TCLruCache cache;
	
	final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

	final int cacheSize = maxMemory / 4;
	
	public static LoadImageCache loader;
	
	public LoadImageCache(Context context) {
	    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    int memoryClass = am.getMemoryClass() * 1024 * 1024;
	    cache = new TCLruCache(memoryClass);
	}

	public void display(String url, ImageView imageview) {
	    Bitmap image = cache.get(url);
	    if (image != null) {
	        imageview.setImageBitmap(image);
	    }
	    else {
	        new SetImageTask(imageview).execute(url);
	    }
	}

	private class TCLruCache extends LruCache<String, Bitmap> {

	    public TCLruCache(int maxSize) {
	        super(maxSize);
	    }
	    

	}

	private class SetImageTask extends AsyncTask<String, Void, Integer> {
	    private ImageView imageview;
	    private Bitmap bmp;

	    public SetImageTask(ImageView imageview) {
	        this.imageview = imageview;
	    }

	    @Override
	    protected Integer doInBackground(String... params) {
	        String url = params[0];
	        try {
	            bmp = getBitmapFromURL(url);
	            if (bmp != null)
	                cache.put(url, bmp);
	            else 
	                return 0;
	        } catch (Exception e) {
	           	e.printStackTrace();
	            return 0;
	        }
	        return 1;
	    }

	    @Override
	    protected void onPostExecute(Integer result) {
	        if(result == 1) {
	            imageview.setImageBitmap(bmp);
	        }
	        super.onPostExecute(result);
	    }

	    private Bitmap getBitmapFromURL(String src) {
	        try {
	            URL url = new URL(src);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setInstanceFollowRedirects(true);
	            connection.setDoInput(true);
	            connection.connect();
	            InputStream input = connection.getInputStream();
	            
	            BitmapFactory.Options options = new BitmapFactory.Options();
	            //options.inJustDecodeBounds = true;
	            options.inSampleSize = 2; 
	            
	            Bitmap myBitmap = BitmapFactory.decodeStream(input, null, options);
	            return myBitmap;
	        } catch (IOException e) {
	            //e.printStackTrace();
	            return null;
	        }
	    }

	}
}


