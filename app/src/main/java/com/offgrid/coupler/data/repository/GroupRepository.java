package com.offgrid.coupler.data.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.offgrid.coupler.data.CouplerRoomDatabase;
import com.offgrid.coupler.data.dao.GroupDao;
import com.offgrid.coupler.data.entity.Group;

import java.util.List;

public class GroupRepository {

    private GroupDao dao;

    public GroupRepository(Application application) {
        CouplerRoomDatabase db = CouplerRoomDatabase.getDatabase(application);
        dao = db.groupDao();
    }

    public LiveData<List<Group>> getGroups() {
        return dao.findAll();
    }

    public LiveData<Group> getUserId(Long id) {
        return dao.findById(id);
    }

    public void insert(final Group group) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(group);
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

    public void delete(final long id) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteById(id);
            }
        });
    }
}
