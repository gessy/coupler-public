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
import com.offgrid.coupler.data.entity.Group;
import com.offgrid.coupler.data.entity.GroupChat;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.entity.UserChat;
import com.offgrid.coupler.data.repository.GroupRepository;
import com.offgrid.coupler.data.repository.MessageRepository;

import java.util.Date;

import static com.offgrid.coupler.data.entity.Chat.groupChat;


public class GroupViewModel extends AndroidViewModel {
    private GroupRepository groupRepository;
    private MessageRepository messageRepository;

    private final MutableLiveData<Long> liveID = new MutableLiveData();
    private LiveData<GroupChat> liveGroupChat;

    public GroupViewModel(Application application) {
        super(application);
        groupRepository = new GroupRepository(application);
        messageRepository = new MessageRepository(application);
        liveGroupChat = Transformations.switchMap(liveID, new Function<Long, LiveData<GroupChat>>() {
            @Override
            public LiveData<GroupChat> apply(Long groupId) {
                return (groupId != null)
                        ? groupRepository.getGroupChat(groupId)
                        : new MediatorLiveData<GroupChat>();
            }
        });
    }

    public void loadByGroupId(Long groupId) {
        liveID.setValue(groupId);
    }

    public Group getGroup() {
        GroupChat groupChat = liveGroupChat.getValue();
        return (groupChat != null) ? groupChat.group : null;
    }

    public void delete() {
        GroupChat groupChat = liveGroupChat.getValue();
        if (groupChat != null) {
            if (groupChat.chat != null) {
                messageRepository.deleteChatMessages(groupChat.chat.getId());
            }

            groupRepository.delete(groupChat);
        }
    }


    public void allowNotification(boolean allow) {
        GroupChat groupChat = liveGroupChat.getValue();
        if (groupChat != null) {
            Group group = groupChat.group;
            group.setAllowNotify(allow);
            groupRepository.update(group);
        }
    }


    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super GroupChat> observer) {
        liveGroupChat.observe(owner, observer);
    }

    public void insert(Group group) {
        groupRepository.insert(new GroupChat(group, groupChat(group.getName())));
    }

    public void updateGroup(String name, String description) {
        GroupChat groupChat = liveGroupChat.getValue();
        if (groupChat != null) {
            groupChat.group.setName(name);
            groupChat.group.setDescription(description);
            groupChat.chat.setTitle(name);
            groupChat.chat.setLastModificationDate(new Date());
            groupRepository.update(groupChat);
        }
    }

}
