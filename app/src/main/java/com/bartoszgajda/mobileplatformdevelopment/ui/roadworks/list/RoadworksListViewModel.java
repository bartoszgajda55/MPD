package com.bartoszgajda.mobileplatformdevelopment.ui.roadworks.list;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bartoszgajda.mobileplatformdevelopment.util.http.AsyncResponse;
import com.bartoszgajda.mobileplatformdevelopment.util.http.SendHttpRequestTask;
import com.bartoszgajda.mobileplatformdevelopment.util.model.Roadwork;
import com.bartoszgajda.mobileplatformdevelopment.util.parser.RoadworksXmlParser;

import java.util.ArrayList;

public class RoadworksListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RoadworksListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Roadworks List fragment");
        new SendHttpRequestTask("https://trafficscotland.org/rss/feeds/roadworks.aspx", new RoadworksXmlParser(),
            new AsyncResponse() {
                @Override
                public void processFinish(ArrayList<?> output) {
                    ArrayList<Roadwork> roadworks = (ArrayList<Roadwork>) output;
                    Log.d("api", roadworks.get(0).toString());
                }
            }).execute();
    }

    public LiveData<String> getText() {
        return mText;
    }
}