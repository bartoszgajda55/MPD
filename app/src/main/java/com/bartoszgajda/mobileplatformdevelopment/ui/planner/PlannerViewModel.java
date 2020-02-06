package com.bartoszgajda.mobileplatformdevelopment.ui.planner;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bartoszgajda.mobileplatformdevelopment.util.http.AsyncResponse;
import com.bartoszgajda.mobileplatformdevelopment.util.http.SendHttpRequestTask;
import com.bartoszgajda.mobileplatformdevelopment.util.model.PlannedRoadwork;
import com.bartoszgajda.mobileplatformdevelopment.util.parser.PlannedRoadworksXmlParser;

import java.util.ArrayList;

public class PlannerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PlannerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Planner fragment");
        new SendHttpRequestTask("https://trafficscotland.org/rss/feeds/plannedroadworks.aspx", new PlannedRoadworksXmlParser(),
            new AsyncResponse() {
                @Override
                public void processFinish(ArrayList<?> output) {
                    ArrayList<PlannedRoadwork> roadworks = (ArrayList<PlannedRoadwork>) output;
                    Log.d("api", roadworks.get(0).toString());
                }
            }).execute();
    }

    public LiveData<String> getText() {
        return mText;
    }
}