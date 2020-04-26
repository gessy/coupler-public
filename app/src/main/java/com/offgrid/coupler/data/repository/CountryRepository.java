package com.offgrid.coupler.data.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.offgrid.coupler.data.CouplerRoomDatabase;
import com.offgrid.coupler.data.dao.CountryRegionDao;
import com.offgrid.coupler.data.entity.Country;
import com.offgrid.coupler.data.entity.CountryRegions;

import java.util.List;

public class CountryRepository {

    private CountryRegionDao dao;

    public CountryRepository(Application application) {
        CouplerRoomDatabase db = CouplerRoomDatabase.getDatabase(application);
        dao = db.countryRegionDao();
    }

    public LiveData<List<CountryRegions>> getCountryRegions() {
        return dao.findAll();
    }

    public LiveData<List<Country>> getCountries() {
        return dao.findAllCountries();
    }

}
