package com.example.restclient;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

public class RestClientUtils {

//    private static final String BASE_URL = "http://localhost:8080/";
    private static final String BASE_URL = "http://corona-api.com/";


    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getAbsoluteUrl(url), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
