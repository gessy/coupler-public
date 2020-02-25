package com.offgrid.coupler.ui.chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ChatListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is chat list fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}