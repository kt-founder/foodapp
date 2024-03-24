package com.example.myfoodapp.ui.clorine;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
public class CaloViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public CaloViewModel() {
        //this.mText = mText;
        mText = new MutableLiveData<>();
        mText.setValue("Day la trang dinh duong");
    }
    public LiveData<String> getText(){return mText;}
}
