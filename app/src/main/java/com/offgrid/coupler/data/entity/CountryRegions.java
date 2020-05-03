package com.offgrid.coupler.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Relation;

import java.util.List;

public class CountryRegions {
    @ColumnInfo(name = "cid")
    public Long countryId;


    @Relation(
            entity = Country.class,
            parentColumn = "cid",
            entityColumn = "id"
    )
    public Country country;


    @Relation(
            entity = Region.class,
            parentColumn = "cid",
            entityColumn = "country_id"
    )
    public List<Region> regions;
}
