package com.bartoszgajda.mobileplatformdevelopment.ui.incidents.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IncidentsListViewModel extends ViewModel {

  private MutableLiveData<String> mText;

  public IncidentsListViewModel() {
    mText = new MutableLiveData<>();
    mText.setValue("This is List");
  }

  public LiveData<String> getText() {
    return mText;
  }
}
