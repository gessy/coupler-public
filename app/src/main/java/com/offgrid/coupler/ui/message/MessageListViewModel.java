package com.offgrid.coupler.ui.message;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.offgrid.coupler.data.entity.Message;
import com.offgrid.coupler.data.repository.MessageRepository;

import java.util.List;

public class MessageListViewModel extends AndroidViewModel {

    private MessageRepository messageRepository;

    public MessageListViewModel(Application application) {
        super(application);
        messageRepository = new MessageRepository(application);
    }

    LiveData<List<Message>> getChatMessages(long chat_id) {
        return messageRepository.getChatMessages(chat_id);
    }

    void insert(Message message) {
        messageRepository.insert(message);
    }
}
