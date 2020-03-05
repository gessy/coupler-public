package com.offgrid.coupler.ui.chat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.data.repository.ChatRepository;

import java.util.ArrayList;
import java.util.List;

public class ChatListViewModel extends AndroidViewModel {

    private ChatRepository chatRepository;
    private LiveData<List<Chat>> liveChats;

    public ChatListViewModel(Application application) {
        super(application);
        chatRepository = new ChatRepository(application);
    }

    void loadChats() {
        liveChats = chatRepository.getChats();
    }

    List<Chat> getChats() {
        return liveChats !=null ? liveChats.getValue() : new ArrayList<Chat>();
    }

    void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<Chat>> observer) {
        liveChats.observe(owner, observer);
    }

    void insertChat(Chat chat) {
        chatRepository.insert(chat);
    }
}
