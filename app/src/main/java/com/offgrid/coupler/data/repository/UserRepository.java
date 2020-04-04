package com.offgrid.coupler.data.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.offgrid.coupler.data.CouplerRoomDatabase;
import com.offgrid.coupler.data.dao.UserChatDao;
import com.offgrid.coupler.data.dao.UserDao;
import com.offgrid.coupler.data.entity.User;
import com.offgrid.coupler.data.entity.UserChat;
import com.offgrid.coupler.data.entity.UserChatMessages;

import java.util.List;

public class UserRepository {

    private UserDao dao;
    private UserChatDao userChatDao;

    public UserRepository(Application application) {
        CouplerRoomDatabase db = CouplerRoomDatabase.getDatabase(application);
        dao = db.userDao();
        userChatDao = db.userChatDao();
    }

    public LiveData<List<User>> getUsers() {
        return dao.findAll();
    }

    public LiveData<List<User>> getOrderedUsers() {
        return dao.findAllOrderedByLastSeenDate();
    }


    public LiveData<UserChat> getUserChatByUserId(Long userId) {
        return dao.findUserChatByUserId(userId);
    }

    public LiveData<UserChat> getUserChatByGid(String gid) {
        return dao.findUserChatByGid(gid);
    }


    public LiveData<UserChatMessages> getUserChatMessagesByUserId(Long userId) {
        return dao.findUserChatMessagesByUserId(userId);
    }


    public void insert(final UserChat userChat) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userChatDao.insert(userChat);
            }
        });
    }

    public void update(final User user) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.update(user);
            }
        });
    }

    public void update(final UserChat userChat) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userChatDao.update(userChat);
            }
        });
    }


    public void delete(final UserChat userChat) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userChatDao.delete(userChat);
            }
        });
    }

}
