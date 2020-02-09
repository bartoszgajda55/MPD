package com.bartoszgajda.mobileplatformdevelopment.ui.incidents.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IncidentsMapViewModel extends ViewModel {

  private MutableLiveData<String> mText;

  public IncidentsMapViewModel() {
    mText = new MutableLiveData<>();
    mText.setValue("This is Map");
  }

  public LiveData<String> getText() {
    return mText;
  }
}
