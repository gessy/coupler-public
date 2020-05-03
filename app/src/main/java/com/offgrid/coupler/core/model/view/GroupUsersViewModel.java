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

import com.offgrid.coupler.data.entity.GroupUsers;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.entity.UserGroupRef;
import com.offgrid.coupler.data.repository.GroupRepository;

import java.util.ArrayList;
import java.util.List;


public class GroupUsersViewModel extends AndroidViewModel {

    private GroupRepository groupRepository;

    private final MutableLiveData<Long> liveID = new MutableLiveData();
    private LiveData<GroupUsers> liveGroupUsers;

    private LifecycleOwner currentOwner;

    public GroupUsersViewModel(Application application) {
        super(application);
        groupRepository = new GroupRepository(application);

        liveGroupUsers = Transformations.switchMap(liveID, new Function<Long, LiveData<GroupUsers>>() {
            @Override
            public LiveData<GroupUsers> apply(Long groupId) {
                return (groupId != null)
                        ? groupRepository.getGroupUsersByGroupId(groupId)
                        : new MediatorLiveData<GroupUsers>();
            }
        });
    }

    public void loadByOwnerId(Long RefId) {
        liveID.setValue(RefId);
    }

    public void setUsers(List<User> users) {
        GroupUsers groupUsers = liveGroupUsers.getValue();
        if (groupUsers != null) {
            groupUsers.users = users;
            groupRepository.upsert(groupUsers);
        }
    }

    public List<User> getUsers() {
        GroupUsers groupUsers = liveGroupUsers.getValue();
        return groupUsers != null ? groupUsers.users : new ArrayList<User>();
    }

    public void removeUser(User user) {
        GroupUsers groupUsers = liveGroupUsers.getValue();
        if (groupUsers != null) {
            groupRepository.delete(new UserGroupRef(user.getId(), groupUsers.group.getId()));
        }
    }

    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super GroupUsers> observer) {
        if (currentOwner != null) {
            liveGroupUsers.removeObservers(currentOwner);
        }
        currentOwner = owner;
        liveGroupUsers.observe(currentOwner, observer);
    }

}
