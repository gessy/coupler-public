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
import com.offgrid.coupler.data.entity.GroupChatMessages;
import com.offgrid.coupler.data.entity.Message;
import com.offgrid.coupler.data.repository.ChatRepository;
import com.offgrid.coupler.data.repository.GroupRepository;
import com.offgrid.coupler.data.repository.MessageRepository;

import java.util.Date;
import java.util.List;

import static com.offgrid.coupler.data.entity.Message.myMessage;
import static com.offgrid.coupler.data.model.ChatType.PERSONAL;


public class GroupChatViewModel extends AndroidViewModel implements ChatViewModel {

    private GroupRepository groupRepository;
    private ChatRepository chatRepository;
    private MessageRepository messageRepository;

    private final MutableLiveData<Long> liveID = new MutableLiveData();
    private LiveData<GroupChatMessages> liveChat;

    private LifecycleOwner currentOwner;

    public GroupChatViewModel(Application application) {
        super(application);
        groupRepository = new GroupRepository(application);
        chatRepository = new ChatRepository(application);
        messageRepository = new MessageRepository(application);

        liveChat = Transformations.switchMap(liveID, new Function<Long, LiveData<GroupChatMessages>>() {
            @Override
            public LiveData<GroupChatMessages> apply(Long groupId) {
                return (groupId != null)
                        ? groupRepository.getGroupChatMessagesByGroupId(groupId)
                        : new MediatorLiveData<GroupChatMessages>();
            }
        });
    }

    @Override
    public void loadByReferenceId(Long RefId) {
        liveID.setValue(RefId);
    }

    @Override
    public void addMessage(Message message) {
        GroupChatMessages groupChatMessages = liveChat.getValue();
        if (groupChatMessages != null) {
            Chat chat = groupChatMessages.chat;
            chat.setLastMessage(message.getMessage());
            chat.setLastModificationDate(new Date());
            chatRepository.update(chat);

            message.setChatId(chat.getId());
            messageRepository.insert(message);
        }
    }


    @Override
    public void deleteMessages() {
        GroupChatMessages groupChatMessages = liveChat.getValue();
        if (groupChatMessages != null) {
            Chat chat = groupChatMessages.chat;
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
