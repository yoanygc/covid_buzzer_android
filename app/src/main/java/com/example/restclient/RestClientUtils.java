package com.example.restclient;

import com.loopj.android.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RestClientUtils {

    private  String restResponse;

    public static void get(String url, RequestParams params) {


        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.get(url,params, new JsonHttpResponseHandler(){


                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray

                try {
                    String restResponse = response.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            });


    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, responseHandler);
    }

}
