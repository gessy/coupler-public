package com.offgrid.coupler.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.mapbox.mapboxsdk.geometry.LatLng;

@Entity(tableName = "T_Place")
public class Place {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Long id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "info")
    private String info;

    @ColumnInfo(name = "latitude")
    public double latitude;

    @ColumnInfo(name = "longitude")
    public double longitude;

    @NonNull
    @ColumnInfo(name = "placelist_id")
    private Long placelistId;


    public Place() {
    }

    @Ignore
    public Place(@NonNull String name, @NonNull String info) {
        this.name = name;
        this.info = info;
    }

    @Ignore
    public Place(@NonNull String name,
                 @NonNull String info,
                 double latitude,
                 double longitude,
                 @NonNull Long placelistId) {
        this.name = name;
        this.info = info;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placelistId = placelistId;
    }

    public static Place empty() {
        return new Place("");
    }

    public void updateWith(Place that) {
        this.name = that.name;
        this.info = that.info;
        this.placelistId = that.placelistId;
        this.latitude = that.latitude;
        this.longitude = that.longitude;
    }

    @Ignore
    public Place(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }


    @NonNull
    public String getInfo() {
        return info;
    }

    public void setInfo(@NonNull String info) {
        this.info = info;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @NonNull
    public Long getPlacelistId() {
        return placelistId;
    }

    public void setPlacelistId(@NonNull Long placelistId) {
        this.placelistId = placelistId;
    }

    public LatLng getLocation() {
        return new LatLng(latitude, longitude);
    }
}
