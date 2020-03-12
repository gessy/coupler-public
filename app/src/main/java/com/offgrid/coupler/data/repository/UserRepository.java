package com.offgrid.coupler.data.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.offgrid.coupler.data.CouplerRoomDatabase;
import com.offgrid.coupler.data.dao.UserDao;
import com.offgrid.coupler.data.entity.User;

import java.util.List;

public class UserRepository {

    private UserDao dao;

    public UserRepository(Application application) {
        CouplerRoomDatabase db = CouplerRoomDatabase.getDatabase(application);
        dao = db.userDao();
    }

    public LiveData<List<User>> getUsers() {
        return dao.findAll();
    }

    public LiveData<User> getUserByGid(String gid) {
        return dao.findBuGid(gid);
    }

    public void insert(final User user) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(user);
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

    public void delete(final long id) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteById(id);
            }
        });
    }

    public void delete(final String gid) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteByGid(gid);
            }
        });
    }
}
