package com.example.restclient.ui.home;

import android.widget.TextView;

import com.example.restclient.RemoteServiceDataProvider;
import com.example.restclient.RestClientUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import cz.msebera.android.httpclient.Header;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> footText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        footText = new MutableLiveData<>();

//        mText.setValue("This is home fragment");
//        footText.setValue("Rest API:");





    }

    public LiveData<String> getText() {
        return mText;
    }

}