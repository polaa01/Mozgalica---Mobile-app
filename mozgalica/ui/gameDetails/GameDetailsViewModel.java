package com.example.mozgalica.ui.gameDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameDetailsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GameDetailsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is game details fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}