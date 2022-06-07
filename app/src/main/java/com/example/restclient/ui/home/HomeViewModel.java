package com.example.restclient.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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