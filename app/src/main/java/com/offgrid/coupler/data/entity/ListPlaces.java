package com.offgrid.coupler.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Relation;

import java.util.List;

public class ListPlaces {
    @ColumnInfo(name = "lid")
    public Long listId;

    @Relation(
            entity = Placelist.class,
            parentColumn = "lid",
            entityColumn = "id"
    )
    public Placelist placelist;

    @Relation(
            entity = Place.class,
            parentColumn = "lid",
            entityColumn = "placelist_id"
    )
    public List<Place> place;
}
