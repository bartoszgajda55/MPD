package com.bartoszgajda.mobileplatformdevelopment.ui.incidents;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bartoszgajda.mobileplatformdevelopment.util.SendHttpRequestTask;

public class IncidentsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public IncidentsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Incidents fragment");
        new SendHttpRequestTask("https://trafficscotland.org/rss/feeds/currentincidents.aspx").execute();
    }

    public LiveData<String> getText() {
        return mText;
    }
}