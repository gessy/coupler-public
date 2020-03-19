package com.offgrid.coupler.core.model.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
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
import com.offgrid.coupler.data.entity.UserChatMessages;
import com.offgrid.coupler.data.repository.ChatRepository;
import com.offgrid.coupler.data.repository.MessageRepository;
import com.offgrid.coupler.data.repository.UserRepository;

import java.util.Date;
import java.util.List;

import static com.offgrid.coupler.data.model.ChatType.PERSONAL;


public class UserChatViewModel extends AndroidViewModel implements ChatViewModel {

    private UserRepository userRepository;
    private ChatRepository chatRepository;
    private MessageRepository messageRepository;

    private final MutableLiveData<Long> liveID = new MutableLiveData();
    private LiveData<UserChatMessages> liveChat;

    private LifecycleOwner currentOwner;

    public UserChatViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
        chatRepository = new ChatRepository(application);
        messageRepository = new MessageRepository(application);

        liveChat = Transformations.switchMap(liveID, new Function<Long, LiveData<UserChatMessages>>() {
            @Override
            public LiveData<UserChatMessages> apply(Long userId) {
                return (userId != null)
                        ? userRepository.getUserChatMessagesByUserId(userId)
                        : new MediatorLiveData<UserChatMessages>();
            }
        });
    }

    @Override
    public void loadByReferenceId(Long RefId) {
        liveID.setValue(RefId);
    }

    @Override
    public void addMessage(Message message) {
        UserChatMessages userChatMessages = liveChat.getValue();
        if (userChatMessages != null) {
            Chat chat = userChatMessages.chat;
            chat.setLastMessage(message.getMessage());
            chat.setLastModificationDate(new Date());
            chatRepository.update(chat);

            message.setChatId(chat.getId());
            messageRepository.insert(message);
        }
    }

    @Override
    public void deleteMessages() {
        UserChatMessages userChatMessages = liveChat.getValue();
        if (userChatMessages != null) {
            Chat chat = userChatMessages.chat;
            messageRepository.deleteChatMessages(chat.getId());

            chat.setLastMessage("");
            chat.setLastModificationDate(new Date());
            chatRepository.update(chat);
        }
    }

    @Override
    public boolean noMessages() {
        List<Message> messages = liveChat.getValue().messages;
        return messages == null || messages.isEmpty();
    }

    @Override
    public boolean isPersonal() {
        Chat chat = liveChat.getValue().chat;
        return PERSONAL.equals(chat.getType());
    }

    @Override
    public void setVisibility(boolean visible) {
        Chat chat = liveChat.getValue().chat;
        chat.setVisible(visible);
        chatRepository.update(chat);
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super ChatMessages> observer) {
        if (currentOwner != null) {
            liveChat.removeObservers(currentOwner);
        }
        currentOwner = owner;
        liveChat.observe(currentOwner, observer);
    }

}
