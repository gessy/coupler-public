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
import com.offgrid.coupler.data.repository.GroupRepository;


public class GroupViewModel extends AndroidViewModel {
    private GroupRepository groupRepository;

    private final MutableLiveData<Long> liveID = new MutableLiveData();
    private LiveData<GroupChat> liveGroupChat;

    public GroupViewModel(Application application) {
        super(application);
        groupRepository = new GroupRepository(application);
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
            groupRepository.delete(groupChat);
        }
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super GroupChat> observer) {
        liveGroupChat.observe(owner, observer);
    }

    public void insert(Group group) {
        groupRepository.insert(new GroupChat(group, Chat.groupChat(group.getName())));
    }
}
