package com.bartoszgajda.mobileplatformdevelopment.ui.incidents.map;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bartoszgajda.mobileplatformdevelopment.util.http.AsyncResponse;
import com.bartoszgajda.mobileplatformdevelopment.util.http.SendHttpRequestTask;
import com.bartoszgajda.mobileplatformdevelopment.util.model.Incident;
import com.bartoszgajda.mobileplatformdevelopment.util.parser.IncidentsXmlParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bartosz Gajda
 * @matricNumber S1631175
 */
public class IncidentsMapViewModel extends ViewModel {

  private MutableLiveData<List<Incident>> mIncidents;

  public IncidentsMapViewModel() {
    mIncidents = new MutableLiveData<>();
    new SendHttpRequestTask("https://trafficscotland.org/rss/feeds/currentincidents.aspx", new IncidentsXmlParser(),
        new AsyncResponse() {
          @Override
          public void processFinish(ArrayList<?> output) {
            List<Incident> incidents = (List<Incident>) output;
            mIncidents.setValue(incidents);
            Log.d("api", incidents.get(0).toString());
          }
        }).execute();
  }

  public LiveData<List<Incident>> getIncidents() {
    return this.mIncidents;
  }
}