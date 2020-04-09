package com.offgrid.coupler.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.offgrid.coupler.data.CouplerRoomDatabase;
import com.offgrid.coupler.data.dao.PlacelistDao;
import com.offgrid.coupler.data.entity.Placelist;

import java.util.List;

public class PlacelistRepository {

    private PlacelistDao dao;

    public PlacelistRepository(Application application) {
        CouplerRoomDatabase db = CouplerRoomDatabase.getDatabase(application);
        dao = db.placelistDao();
    }

    public LiveData<List<Placelist>> getLists() {
        return dao.findAll();
    }


    public void delete(final long list_id) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(list_id);
            }
        });
    }

    public void update(final Placelist list) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.update(list);
            }
        });
    }

    public void insert(final Placelist list) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(list);
            }
        });
    }

}
