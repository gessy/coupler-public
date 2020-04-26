package com.offgrid.coupler.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Relation;


public class RegionCountry {
    @ColumnInfo(name = "rid")
    public Long regionId;

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
            parentColumn = "rid",
            entityColumn = "id"
    )
    public Country region;
}
