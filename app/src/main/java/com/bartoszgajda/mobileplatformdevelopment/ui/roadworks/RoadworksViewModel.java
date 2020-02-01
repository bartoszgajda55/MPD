package com.bartoszgajda.mobileplatformdevelopment.ui.roadworks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RoadworksViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RoadworksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Roadworks fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}