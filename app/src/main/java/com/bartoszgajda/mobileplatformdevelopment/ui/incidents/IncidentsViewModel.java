package com.bartoszgajda.mobileplatformdevelopment.ui.incidents;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.bartoszgajda.mobileplatformdevelopment.util.http.AsyncResponse;
import com.bartoszgajda.mobileplatformdevelopment.util.http.SendHttpRequestTask;
import com.bartoszgajda.mobileplatformdevelopment.util.model.Incident;

import java.util.ArrayList;

public class IncidentsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public IncidentsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Incidents fragment");
        new SendHttpRequestTask("https://trafficscotland.org/rss/feeds/currentincidents.aspx", new AsyncResponse() {
          @Override
          public void processFinish(ArrayList<? extends Object> output) {
            ArrayList<Incident> incidents = (ArrayList<Incident>) output;
            Log.d("api", incidents.get(0).toString());
          }
        }).execute();
    }

    public LiveData<String> getText() {
        return mText;
    }
}