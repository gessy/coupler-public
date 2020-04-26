package com.offgrid.coupler.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.mapbox.mapboxsdk.geometry.LatLng;


@Entity(tableName = "T_Region")
public class Region {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Long id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "country_id")
    private Long countryId;

    @NonNull
    @ColumnInfo(name = "north_east_latitude")
    private double northEastLatitude;

    @NonNull
    @ColumnInfo(name = "north_east_longitude")
    private double northEastLongitude;

    @NonNull
    @ColumnInfo(name = "south_west_latitude")
    private double southWestLatitude;

    @NonNull
    @ColumnInfo(name = "south_west_longitude")
    private double southWestLongitude;

    @NonNull
    @ColumnInfo(name = "min_zoom")
    private int minZoom;

    @NonNull
    @ColumnInfo(name = "max_zoom")
    private int maxZoom;

    @NonNull
    @ColumnInfo(name = "is_downloaded")
    private boolean isDownloaded;


    public Region() {
    }


    @Ignore
    public Region(@NonNull String name,
                  @NonNull Long countryId,
                  double northEastLatitude,
                  double northEastLongitude,
                  double southWestLatitude,
                  double southWestLongitude,
                  int minZoom,
                  int maxZoom) {
        this.name = name;
        this.countryId = countryId;
        this.northEastLatitude = northEastLatitude;
        this.northEastLongitude = northEastLongitude;
        this.southWestLatitude = southWestLatitude;
        this.southWestLongitude = southWestLongitude;
        this.minZoom = minZoom;
        this.maxZoom = maxZoom;
        this.isDownloaded = false;
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
    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(@NonNull Long countryId) {
        this.countryId = countryId;
    }

    public double getNorthEastLatitude() {
        return northEastLatitude;
    }

    public void setNorthEastLatitude(double northEastLatitude) {
        this.northEastLatitude = northEastLatitude;
    }

    public double getNorthEastLongitude() {
        return northEastLongitude;
    }

    public void setNorthEastLongitude(double northEastLongitude) {
        this.northEastLongitude = northEastLongitude;
    }

    public double getSouthWestLatitude() {
        return southWestLatitude;
    }

    public void setSouthWestLatitude(double southWestLatitude) {
        this.southWestLatitude = southWestLatitude;
    }

    public double getSouthWestLongitude() {
        return southWestLongitude;
    }

    public void setSouthWestLongitude(double southWestLongitude) {
        this.southWestLongitude = southWestLongitude;
    }

    public int getMinZoom() {
        return minZoom;
    }

    public void setMinZoom(int minZoom) {
        this.minZoom = minZoom;
    }

    public int getMaxZoom() {
        return maxZoom;
    }

    public void setMaxZoom(int maxZoom) {
        this.maxZoom = maxZoom;
    }

    public LatLng getNorthEastLocation() {
        return new LatLng(northEastLatitude, northEastLongitude);
    }

    public LatLng getSouthWestLocation() {
        return new LatLng(southWestLatitude, southWestLongitude);
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }
}
