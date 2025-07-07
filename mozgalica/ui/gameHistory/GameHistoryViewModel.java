package com.example.mozgalica.ui.gameHistory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GameHistoryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GameHistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is game history fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}