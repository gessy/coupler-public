package com.offgrid.coupler.data.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.offgrid.coupler.data.CouplerRoomDatabase;
import com.offgrid.coupler.data.dao.CountryRegionDao;
import com.offgrid.coupler.data.entity.CountryRegions;
import com.offgrid.coupler.data.entity.Region;
import com.offgrid.coupler.data.entity.RegionCountry;

import java.util.List;

public class RegionRepository {

    private CountryRegionDao dao;

    public RegionRepository(Application application) {
        CouplerRoomDatabase db = CouplerRoomDatabase.getDatabase(application);
        dao = db.countryRegionDao();
    }

    public LiveData<List<Region>> getRegions(Long countryId) {
        return dao.findAllCountryRegions(countryId);
    }

    public LiveData<Region> getRegion(Long regionId) {
        return dao.findRegionById(regionId);
    }


    public LiveData<List<RegionCountry>> getLoadedRegionCountry() {
        return dao.findLoadedRegionCountry();
    }


    public LiveData<List<Region>> getRegionsToDownload(Long countryId) {
        return dao.findCountryRegionsToDownload(countryId);
    }

    public LiveData<CountryRegions> getCountryRegions(Long countryId) {
        return dao.findCountryRegions(countryId);
    }

    public void update(final Region region) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.update(region);
            }
        });
    }

}
