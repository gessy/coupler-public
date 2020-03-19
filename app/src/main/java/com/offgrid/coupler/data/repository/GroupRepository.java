package com.offgrid.coupler.data.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.offgrid.coupler.data.CouplerRoomDatabase;
import com.offgrid.coupler.data.dao.GroupDao;
import com.offgrid.coupler.data.entity.ChatMessages;
import com.offgrid.coupler.data.entity.Group;
import com.offgrid.coupler.data.entity.GroupChat;
import com.offgrid.coupler.data.entity.GroupChatMessages;


public class GroupRepository {

    private GroupDao dao;

    public GroupRepository(Application application) {
        CouplerRoomDatabase db = CouplerRoomDatabase.getDatabase(application);
        dao = db.groupDao();
    }

    public LiveData<GroupChat> getGroupChat(Long id) {
        return dao.findGroupChatByGroupId(id);
    }

    public LiveData<GroupChatMessages> getGroupChatMessagesByGroupId(Long groupId) {
        return dao.findGroupChatMessagesByGroupId(groupId);
    }

    public void insert(final Group group) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(group);
            }
        });
    }

    public void insert(final GroupChat groupChat) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(groupChat);
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

    public void delete(final GroupChat groupChat) {
        if (groupChat != null) {
            CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    dao.delete(groupChat);
                }
            });
        }
    }

    public void delete(final Group group) {
        if (group != null) {
            CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    dao.delete(group);
                }
            });
        }
    }

    public void delete(final long id) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteById(id);
            }
        });
    }
}
