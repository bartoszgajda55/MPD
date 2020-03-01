package com.bartoszgajda.mobileplatformdevelopment.ui.planner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bartoszgajda.mobileplatformdevelopment.util.http.SendHttpRequestTask;
import com.bartoszgajda.mobileplatformdevelopment.util.model.PlannedRoadwork;
import com.bartoszgajda.mobileplatformdevelopment.util.model.Roadwork;
import com.bartoszgajda.mobileplatformdevelopment.util.parser.PlannedRoadworksXmlParser;
import com.bartoszgajda.mobileplatformdevelopment.util.parser.RoadworksXmlParser;

import java.util.List;

/**
 * @author Bartosz Gajda
 * @matricNumber S1631175
 */
public class PlannerViewModel extends ViewModel {

  private MutableLiveData<List<Roadwork>> mRoadworks;
  private MutableLiveData<List<PlannedRoadwork>> mPlannerRoadworks;

  public PlannerViewModel() {
    mRoadworks = new MutableLiveData<>();
    new SendHttpRequestTask("https://trafficscotland.org/rss/feeds/roadworks.aspx", new RoadworksXmlParser(),
        output -> {
          List<Roadwork> roadworks = (List<Roadwork>) output;
          mRoadworks.setValue(roadworks);
        }).execute();

    mPlannerRoadworks = new MutableLiveData<>();
    new SendHttpRequestTask("https://trafficscotland.org/rss/feeds/plannedroadworks.aspx", new PlannedRoadworksXmlParser(),
        output -> {
          List<PlannedRoadwork> plannedRoadworks = (List<PlannedRoadwork>) output;
          mPlannerRoadworks.setValue(plannedRoadworks);
        }).execute();
  }

  public LiveData<List<Roadwork>> getRoadworks() {
    return this.mRoadworks;
  }

  public LiveData<List<PlannedRoadwork>> getPlannedRoadworks() {
    return this.mPlannerRoadworks;
  }
}