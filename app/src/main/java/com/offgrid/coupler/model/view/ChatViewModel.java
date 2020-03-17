package com.offgrid.coupler.model.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.core.util.Pair;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.data.entity.ChatMessages;
import com.offgrid.coupler.data.entity.Message;
import com.offgrid.coupler.data.model.ChatType;
import com.offgrid.coupler.data.repository.ChatRepository;
import com.offgrid.coupler.data.repository.MessageRepository;


import static com.offgrid.coupler.model.view.ChatViewModel.Entity.CHAT;
import static com.offgrid.coupler.model.view.ChatViewModel.Entity.USER;

public class ChatViewModel extends AndroidViewModel {
    public enum Entity {CHAT, USER, GROUP}

    private ChatRepository chatRepository;
    private MessageRepository messageRepository;

    private LiveData<ChatMessages> liveChatMessages;
    private final MutableLiveData<Pair<Entity, Long>> liveID = new MutableLiveData();

    private LifecycleOwner currentOwner;

    public ChatViewModel(Application application) {
        super(application);
        chatRepository = new ChatRepository(application);
        messageRepository = new MessageRepository(application);

        liveChatMessages = Transformations.switchMap(liveID, new Function<Pair<Entity, Long>, LiveData<ChatMessages>>() {
            @Override
            public LiveData<ChatMessages> apply(Pair<Entity, Long> pair) {
                if (pair == null) return new MediatorLiveData<>();

                switch (pair.first) {
                    case CHAT:
                        return chatRepository.getChatMessages(pair.second);
                    case USER:
                        return chatRepository.getUserChatMessages(pair.second);
                }

                return new MediatorLiveData<>();
            }
        });
    }

    public void loadByChatId(Long chatId) {
        liveID.setValue(new Pair<>(CHAT, chatId));
    }

    public void loadByUserId(Long userId) {
        liveID.setValue(new Pair<>(USER, userId));
    }

    public void addMessage(Message message) {
        ChatMessages chatMessages = liveChatMessages.getValue();
        if (chatMessages != null) {
            Chat chat = chatMessages.chat;
            chat.setLastMessage(message.getMessage());
            chat.setLastModificationDate(System.currentTimeMillis());
            chatRepository.update(chat);

            message.setChatId(chat.getId());
            messageRepository.insert(message);
        }
    }

    public void deleteMessages() {
        ChatMessages chatMessages = liveChatMessages.getValue();
        if (chatMessages != null) {
            Chat chat = chatMessages.chat;
            messageRepository.deleteChatMessages(chat.getId());

            chat.setLastMessage("");
            chat.setLastModificationDate(System.currentTimeMillis());
            chatRepository.update(chat);
        }
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super ChatMessages> observer) {
        if (currentOwner != null) {
            liveChatMessages.removeObservers(currentOwner);
        }
        currentOwner = owner;
        liveChatMessages.observe(currentOwner, observer);
    }

    public void insert(Chat chat) {
        chatRepository.insert(chat);
    }

    public void updateTitle(String title) {
        ChatMessages chatMessages = liveChatMessages.getValue();
        if (chatMessages != null) {
            Chat chat = chatMessages.chat;
            chat.setTitle(title);
            chatRepository.update(chat);
        }
    }

    public void update(Chat chat) {
        chatRepository.update(chat);
    }

    public void delete() {
        ChatMessages chatMessages = liveChatMessages.getValue();
        if (chatMessages != null) {
            Chat chat = chatMessages.chat;
            messageRepository.deleteChatMessages(chat.getId());
            chatRepository.deleteChat(chat.getId());
        }
    }

    public void cleanUpAndDelete() {
        ChatMessages chatMessages = liveChatMessages.getValue();
        if (chatMessages != null) {
            removeObservers();
            Chat chat = chatMessages.chat;
            messageRepository.deleteChatMessages(chat.getId());
            chatRepository.deleteChat(chat.getId());
        }
    }

    private void removeObservers() {
        if (currentOwner != null) {
            liveChatMessages.removeObservers(currentOwner);
        }
    }

    public boolean noMessages() {
        ChatMessages chatMessages = liveChatMessages.getValue();
        return chatMessages == null || chatMessages.messages.isEmpty();
    }

    public boolean isPersonal() {
        ChatMessages chatMessages = liveChatMessages.getValue();
        if (chatMessages != null) {
            Chat chat = chatMessages.chat;
            return ChatType.PERSONAL.toString().equals(chat.getType());
        }
        return false;
    }

}
