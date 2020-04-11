package com.offgrid.coupler.data.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.offgrid.coupler.data.CouplerRoomDatabase;
import com.offgrid.coupler.data.dao.PlaceDao;
import com.offgrid.coupler.data.entity.Place;

import java.util.List;


public class PlaceRepository {

    private PlaceDao dao;

    public PlaceRepository(Application application) {
        CouplerRoomDatabase db = CouplerRoomDatabase.getDatabase(application);
        dao = db.placeDao();
    }

    public LiveData<List<Place>> getPlaces() {
        return dao.findAll();
    }


    public void delete(final long id) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(id);
            }
        });
    }


    public void delete(final Place place) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(place);
            }
        });
    }


    public void insert(final Place place) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(place);
            }
        });
    }

    public void update(final Place place) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.update(place);
            }
        });
    }
}
