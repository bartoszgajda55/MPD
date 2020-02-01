package com.bartoszgajda.mobileplatformdevelopment.ui.planner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlannerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PlannerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Planner fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}