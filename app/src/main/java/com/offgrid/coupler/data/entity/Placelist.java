package com.offgrid.coupler.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "T_Placelist")
public class Placelist {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Long id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "show_on_map")
    private Boolean showOnMap;


    public Placelist() {
    }

    public static Placelist empty() {
        return new Placelist("");
    }

    @Ignore
    public Placelist(@NonNull String name) {
        this.name = name;
        this.showOnMap = true;
    }

    @Ignore
    public Placelist(@NonNull String name, @NonNull Boolean showOnMap) {
        this.name = name;
        this.showOnMap = showOnMap;
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
    public Boolean getShowOnMap() {
        return showOnMap;
    }

    public void setShowOnMap(@NonNull Boolean showOnMap) {
        this.showOnMap = showOnMap;
    }
}
