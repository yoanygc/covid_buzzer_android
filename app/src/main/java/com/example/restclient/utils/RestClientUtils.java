package com.example.restclient.utils;

import com.example.restclient.business.GlobalCovidInfo;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;

import cz.msebera.android.httpclient.Header;

public class RestClientUtils {

    private static String restResponse;
    private static final String url = null;
    private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);






    public static GlobalCovidInfo get(String url, AsyncHttpResponseHandler responseHandler) {

        GlobalCovidInfo globalInfo = new GlobalCovidInfo();

        // Call Rest API to load Global information
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject dataObject = response.getJSONObject("data");
                    //Population
                    String population = dataObject.getString("population");
                    globalInfo.setPopulation(Integer.getInteger(population));
                    // Read the array of elements in the TimeLine
                    JSONArray timeLine = dataObject.getJSONArray("timeline");
                    // Get the last day updated object
                    JSONObject object = (JSONObject) timeLine.get(0);
                    // Updated At:
                    String updated_at= object.getString("updated_at");
                    globalInfo.setUpdatedDate(Date.valueOf(updated_at));
                    // Confirmed Cases
                    String confirmed = object.getString("confirmed");
                    globalInfo.setConfirmedCases(Integer.getInteger(confirmed));
                    // Deaths
                    String deaths = object.getString("deaths");
                    globalInfo.setDeaths(Integer.getInteger(deaths));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

        return globalInfo;


    }

    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, responseHandler);
    }

}
