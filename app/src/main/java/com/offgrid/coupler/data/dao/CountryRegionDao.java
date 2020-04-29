package com.offgrid.coupler.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.offgrid.coupler.data.entity.Country;
import com.offgrid.coupler.data.entity.CountryRegions;
import com.offgrid.coupler.data.entity.Region;
import com.offgrid.coupler.data.entity.RegionCountry;

import java.util.List;
import java.util.Map;


@Dao
public abstract class CountryRegionDao {

    @Transaction
    @Query("select id as cid from T_Country ")
    public abstract LiveData<List<CountryRegions>> findAll();

    @Transaction
    @Query("select id as rid, country_id as cid from T_Region where download_state != 'READY_TO_LOAD'")
    public abstract LiveData<List<RegionCountry>> findLoadedRegionCountry();

    @Transaction
    @Query("select id as cid from T_Country where id = :countryId")
    public abstract LiveData<CountryRegions> findCountryRegions(Long countryId);

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = Country.class)
    abstract long _insert(Country country);

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = Region.class)
    abstract long _insert(Region region);

    @Transaction
    public void init(Map<String, List<Region>> countryRegions) {
        _deleteRegion();
        _deleteCountry();
        for (String name : countryRegions.keySet()) {
            long countryId = _insert(new Country(name));
            List<Region> regions = countryRegions.get(name);
            for (Region region : regions) {
                region.setCountryId(countryId);
                _insert(region);
            }
        }
    }

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(Region region);

    @Query("delete from T_Region")
    abstract void _deleteRegion();

    @Query("delete from T_Country")
    abstract void _deleteCountry();

    @Query("select * from T_Country")
    public abstract LiveData<List<Country>> findAllCountries();

    @Query("select * from T_Region where country_id = :countryId ")
    public abstract LiveData<List<Region>> findAllCountryRegions(Long countryId);

    @Query("select * from T_Region where country_id = :countryId and download_state = 'READY_TO_LOAD'")
    public abstract LiveData<List<Region>> findCountryRegionsToDownload(Long countryId);

}
