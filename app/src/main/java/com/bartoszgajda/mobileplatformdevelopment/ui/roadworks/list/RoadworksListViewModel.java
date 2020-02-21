package com.bartoszgajda.mobileplatformdevelopment.ui.roadworks.list;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bartoszgajda.mobileplatformdevelopment.util.http.AsyncResponse;
import com.bartoszgajda.mobileplatformdevelopment.util.http.SendHttpRequestTask;
import com.bartoszgajda.mobileplatformdevelopment.util.model.Incident;
import com.bartoszgajda.mobileplatformdevelopment.util.model.Roadwork;
import com.bartoszgajda.mobileplatformdevelopment.util.parser.RoadworksXmlParser;

import java.util.ArrayList;
import java.util.List;

public class RoadworksListViewModel extends ViewModel {

  private MutableLiveData<List<Roadwork>> mRoadworks;

  public RoadworksListViewModel() {
    mRoadworks = new MutableLiveData<>();
    new SendHttpRequestTask("https://trafficscotland.org/rss/feeds/roadworks.aspx", new RoadworksXmlParser(),
        new AsyncResponse() {
          @Override
          public void processFinish(ArrayList<?> output) {
            List<Roadwork> roadworks = (List<Roadwork>) output;
            mRoadworks.setValue(roadworks);
          }
        }).execute();
  }

  public LiveData<List<Roadwork>> getRoadworks() {
    return this.mRoadworks;
  }
}