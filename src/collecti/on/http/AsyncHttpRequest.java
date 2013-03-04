package collecti.on.http;

import org.apache.http.entity.StringEntity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class AsyncHttpRequest {
	
	private static final String API_URL = SETTINGS_HTTP.API_URL;

	private static AsyncHttpClient client = new AsyncHttpClient();
  
	// GET requests
	public static void GET(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.get(API_URL + url, params, responseHandler);
	}
  
	// POST requests
	public static void POST(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.post(API_URL + url, params, responseHandler);
	}
  
	public static void POST(String url, StringEntity entity, AsyncHttpResponseHandler responseHandler) {
		client.post(null, API_URL + url, entity, "application/json", responseHandler);
	}

}