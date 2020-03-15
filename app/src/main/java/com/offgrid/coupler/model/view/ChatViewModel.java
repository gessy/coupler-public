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
import com.offgrid.coupler.data.repository.ChatRepository;


import static com.offgrid.coupler.model.view.ChatViewModel.Entity.CHAT;
import static com.offgrid.coupler.model.view.ChatViewModel.Entity.USER;

public class ChatViewModel extends AndroidViewModel {
    public enum Entity {CHAT, USER, GROUP}

    private ChatRepository chatRepository;
    private LiveData<Chat> liveChat;
    private final MutableLiveData<Pair<Entity, Long>> liveID = new MutableLiveData();

    private LifecycleOwner curentOwner;

    public ChatViewModel(Application application) {
        super(application);
        chatRepository = new ChatRepository(application);

        liveChat = Transformations.switchMap(liveID, new Function<Pair<Entity, Long>, LiveData<Chat>>() {
            @Override
            public LiveData<Chat> apply(Pair<Entity, Long> pair) {
                if (pair == null) return new MediatorLiveData<>();

                switch (pair.first) {
                    case CHAT:
                        return chatRepository.getChat(pair.second);
                    case USER:
                        return chatRepository.getUserChat(pair.second);
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

    public Chat get() {
        return liveChat.getValue();
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super Chat> observer) {
        if (curentOwner != null) {
            liveChat.removeObservers(curentOwner);
        }
        curentOwner = owner;
        liveChat.observe(curentOwner, observer);
    }

    public void insert(Chat chat) {
        chatRepository.insert(chat);
    }

    public void updateLastMessage(String message) {
        Chat chat = liveChat.getValue();
        if (chat != null) {
            chat.setLastMessage(message);
            chat.setLastModificationDate(System.currentTimeMillis());
            chatRepository.update(chat);
        }
    }


    public void update(Chat chat) {
        chatRepository.update(chat);
    }

    public void delete() {
        Chat chat = liveChat.getValue();
        if (chat != null) {
            chatRepository.deleteChat(chat.getId());
        }
    }

    public void cleanUpAndDelete() {
        Chat chat = liveChat.getValue();
        if (chat != null) {
            removeObservers();
            chatRepository.deleteChat(chat.getId());
        }
    }

    private void removeObservers() {
        if (curentOwner != null) {
            liveChat.removeObservers(curentOwner);
        }
    }
}
