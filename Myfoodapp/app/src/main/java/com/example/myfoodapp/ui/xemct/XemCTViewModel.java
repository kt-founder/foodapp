package com.example.myfoodapp.ui.xemct;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class XemCTViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public XemCTViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}