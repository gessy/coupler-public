package com.offgrid.coupler.ui.message;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.offgrid.coupler.data.entity.Message;
import com.offgrid.coupler.data.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;

public class MessageListViewModel extends AndroidViewModel {

    private MessageRepository messageRepository;

    private LiveData<List<Message>> liveMessages;

    public MessageListViewModel(Application application) {
        super(application);
        messageRepository = new MessageRepository(application);
    }

    void loadChatMessages(long chat_id) {
        liveMessages = messageRepository.getChatMessages(chat_id);
    }

    List<Message> getMessages() {
        return liveMessages != null ? liveMessages.getValue() : new ArrayList<Message>();
    }

    void insertMessage(Message message) {
        messageRepository.insert(message);
    }

    void deleteChatMessages(long chat_id) {
        messageRepository.deleteChatMessages(chat_id);
    }

    void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<Message>> observer) {
        liveMessages.observe(owner, observer);
    }
}
