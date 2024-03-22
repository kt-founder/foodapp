package com.example.myfoodapp.ui.saved;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SavedViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public SavedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Cac cong thuc da luu cua ban o day");
    }
    public LiveData<String> getText() {
        return mText;
    }

}
