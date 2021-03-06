package com.offgrid.coupler.data.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.offgrid.coupler.data.CouplerRoomDatabase;
import com.offgrid.coupler.data.dao.GroupChatDao;
import com.offgrid.coupler.data.dao.GroupDao;
import com.offgrid.coupler.data.dao.UserGroupDao;
import com.offgrid.coupler.data.entity.Group;
import com.offgrid.coupler.data.entity.GroupChat;
import com.offgrid.coupler.data.entity.GroupChatMessages;
import com.offgrid.coupler.data.entity.GroupUsers;
import com.offgrid.coupler.data.entity.UserGroupRef;


public class GroupRepository {

    private GroupDao dao;
    private GroupChatDao groupChatDao;
    private UserGroupDao userGroupDao;

    public GroupRepository(Application application) {
        CouplerRoomDatabase db = CouplerRoomDatabase.getDatabase(application);
        dao = db.groupDao();
        groupChatDao = db.groupChatDao();
        userGroupDao = db.userGroupDao();
    }

    public LiveData<GroupChat> getGroupChat(Long id) {
        return dao.findGroupChatByGroupId(id);
    }

    public LiveData<GroupChatMessages> getGroupChatMessagesByGroupId(Long groupId) {
        return dao.findGroupChatMessagesByGroupId(groupId);
    }

    public LiveData<GroupUsers> getGroupUsersByGroupId(Long groupId) {
        return dao.findGroupUsersByGroupId(groupId);
    }

    public void upsert(final GroupUsers groupUsers) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userGroupDao.upsert(groupUsers);
            }
        });
    }

    public void insert(final GroupChat groupChat) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                groupChatDao.insert(groupChat);
            }
        });
    }

    public void update(final Group group) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.update(group);
            }
        });
    }

    public void update(final GroupChat groupChat) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                groupChatDao.update(groupChat);
            }
        });
    }

    public void delete(final GroupChat groupChat) {
        if (groupChat != null) {
            CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    groupChatDao.delete(groupChat);
                }
            });
        }
    }

    public void delete(final UserGroupRef ref) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userGroupDao.delete(ref);
            }
        });
    }

}
