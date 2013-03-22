package collecti.on.api;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import collecti.on.Login;
import collecti.on.http.AsyncHttpRequest;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class UserAPI {

	public static void login(final Activity activity, final String username, String password) {
		RequestParams requestParams = new RequestParams();
		requestParams.put("username", username);
		requestParams.put("password", password);

		AsyncHttpRequest.POST("/user/login", requestParams, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {
					Log.d("json login", response.toString());
					String user_id = (String) response.get("_id");
					Log.d("json login", user_id);
					APIHandler.setUserId(user_id);
					((Login) activity).login_success(username, true);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
			    Log.d("json login", errorResponse.toString());
			    ((Login) activity).login_success(username, false);
			}
		});
	}
	
	public static void signup(final Activity activity, final String username, final String email, 
			final String password) {
		RequestParams requestParams = new RequestParams();
		requestParams.put("username", username);
		requestParams.put("email", email);
		requestParams.put("password", password);

		AsyncHttpRequest.POST("/user/signup", requestParams, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {
					Log.d("json signup", response.toString());
					String user_id = (String) response.get("_id");
					Log.d("json signup", user_id);
					APIHandler.setUserId(user_id);
					((Login) activity).signup_success(user_id, username, email);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
			    Log.d("json signup", errorResponse.toString());
			}
		});
	}
	
	public static void uploadPicture(String user_id, String picture) {
		RequestParams requestParams = new RequestParams();
		requestParams.put("_id", user_id);
		requestParams.put("picture", picture);

		AsyncHttpRequest.POST("/user/picture", requestParams, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				Log.d("json picture", response.toString());
			}
			
			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
			    Log.d("json picture", errorResponse.toString());
			}
		});
	}
}
