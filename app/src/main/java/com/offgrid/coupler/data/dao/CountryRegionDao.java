package com.offgrid.coupler.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.offgrid.coupler.data.entity.Country;
import com.offgrid.coupler.data.entity.Region;

import java.util.List;
import java.util.Map;


@Dao
public abstract class CountryRegionDao {

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

    @Query("delete from T_Region")
    abstract void _deleteRegion();

    @Query("delete from T_Country")
    abstract void _deleteCountry();

}
