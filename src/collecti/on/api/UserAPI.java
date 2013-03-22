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
	public static String user_id;

	public static String login(final Activity activity, String username, String password) {
		user_id = "";
		RequestParams requestParams = new RequestParams();
		requestParams.put("username", username);
		requestParams.put("password", password);

		AsyncHttpRequest.POST("/user/login", requestParams, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				try {
					Log.d("json", response.toString());
					user_id = (String) response.get("_id");
					Log.d("json", user_id);
					APIHandler.setUserId(user_id);
					((Login) activity).login_success();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(Throwable e, JSONObject errorResponse) {
			    Log.d("json", errorResponse.toString());
			}
		});
		
		return user_id;
	}
}
