package com.offgrid.coupler.core.model.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.data.repository.ChatRepository;

import java.util.List;

public class ChatListViewModel extends AndroidViewModel {

    private ChatRepository chatRepository;
    private LiveData<List<Chat>> liveChats;

    public ChatListViewModel(Application application) {
        super(application);
        chatRepository = new ChatRepository(application);
    }

    public void load() {
        liveChats = chatRepository.getOrdaredChats();
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<Chat>> observer) {
        liveChats.observe(owner, observer);
    }
}
