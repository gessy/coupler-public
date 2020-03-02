package com.offgrid.coupler.ui.chat;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.offgrid.coupler.data.domain.Chat;
import com.offgrid.coupler.data.repository.ChatRepository;

import java.util.List;

public class ChatListViewModel extends AndroidViewModel {

    private ChatRepository chatRepository;
    private LiveData<List<Chat>> chats;

    public ChatListViewModel(Application application) {
        super(application);
        chatRepository = new ChatRepository(application);
        chats = chatRepository.getAllChats();
    }

    LiveData<List<Chat>> getAllChats() {
        return chats;
    }

    void insert(Chat chat) {
        chatRepository.insert(chat);
    }
}
