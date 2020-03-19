package com.offgrid.coupler.core.model.view;

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

import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.entity.UserChat;
import com.offgrid.coupler.data.repository.ChatRepository;
import com.offgrid.coupler.data.repository.MessageRepository;
import com.offgrid.coupler.data.repository.UserRepository;

import static com.offgrid.coupler.core.model.view.ContactViewModel.Entity.GID;
import static com.offgrid.coupler.core.model.view.ContactViewModel.Entity.UID;
import static com.offgrid.coupler.data.entity.Chat.personalChat;
import static java.lang.System.currentTimeMillis;


public class ContactViewModel extends AndroidViewModel {
    public enum Entity {UID, GID}

    private UserRepository userRepository;
    private ChatRepository chatRepository;
    private MessageRepository messageRepository;

    private final MutableLiveData<Pair<Entity, Object>> liveID = new MutableLiveData();
    private LiveData<UserChat> liveUserChat;

    public ContactViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
        chatRepository = new ChatRepository(application);
        messageRepository = new MessageRepository(application);

        liveUserChat = Transformations.switchMap(liveID, new Function<Pair<Entity, Object>, LiveData<UserChat>>() {
            @Override
            public LiveData<UserChat> apply(Pair<Entity, Object> pair) {
                if (pair == null) return new MediatorLiveData<>();

                switch (pair.first) {
                    case UID:
                        return userRepository.getUserChatByUserId((Long) pair.second);
                    case GID:
                        return userRepository.getUserChatByGid((String) pair.second);
                }

                return new MediatorLiveData<>();
            }
        });
    }

    public void loadByGid(String gid) {
        liveID.setValue(new Pair<>(GID, (Object) gid));
    }

    public void loadByUserId(Long uid) {
        liveID.setValue(new Pair<>(UID, (Object) uid));
    }

    public User getUser() {
        UserChat userChat = liveUserChat.getValue();
        return userChat != null ? userChat.user : null;
    }

    public void delete() {
        UserChat userChat = liveUserChat.getValue();
        if (userChat != null) {
            if (userChat.chat != null) {
                messageRepository.deleteChatMessages(userChat.chat.getId());
                chatRepository.deleteChat(userChat.chat.getId());
            }
            userRepository.delete(userChat.user.getId());
        }
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super UserChat> observer) {
        liveUserChat.observe(owner, observer);
    }

    public void insert(User user) {
        userRepository.insert(new UserChat(user, personalChat(user.chatTitle())));
    }


    public void allowNotification(boolean allow) {
        UserChat userChat = liveUserChat.getValue();
        if (userChat != null) {
            User user = userChat.user;
            user.setAllowNotify(allow);
            userRepository.update(user);
        }
    }

    public void updateFullName(String firstName, String lastName) {
        UserChat userChat = liveUserChat.getValue();
        if (userChat != null) {
            userChat.user.setFirstName(firstName);
            userChat.user.setLastName(lastName);
            userChat.chat.setTitle(userChat.user.chatTitle());
            userChat.chat.setLastModificationDate(currentTimeMillis());

            userRepository.update(userChat);
        }
    }

}
