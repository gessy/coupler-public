package com.offgrid.coupler.ui.message;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.offgrid.coupler.data.entity.Message;
import com.offgrid.coupler.data.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;

public class MessageListViewModel extends AndroidViewModel {

    private MessageRepository messageRepository;

    private final MutableLiveData<Long> liveChatId = new MutableLiveData();

    private boolean newChat;

    private LiveData<List<Message>> liveMessages;

    public MessageListViewModel(Application application) {
        super(application);
        messageRepository = new MessageRepository(application);

        liveMessages = Transformations.switchMap(liveChatId, new Function<Long, LiveData<List<Message>>>() {
            @Override
            public LiveData<List<Message>> apply(Long chatId) {
                return messageRepository.getChatMessages(chatId);
            }
        });
    }


    void loadChatMessages(@NonNull Long chatId) {
        liveChatId.setValue(chatId);
    }

    List<Message> getMessages() {
        return liveMessages != null ? liveMessages.getValue() : new ArrayList<Message>();
    }


    void insert(Message message) {
        Long chatId = liveChatId.getValue();
        if (chatId != null) {
            message.setChatId(chatId);
            messageRepository.insert(message);
        }
    }

    void delete() {
        Long chatId = liveChatId.getValue();
        if (chatId != null) {
            messageRepository.deleteChatMessages(chatId);
        }
    }

    void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super List<Message>> observer) {
        liveMessages.observe(owner, observer);
    }
}
