package com.offgrid.coupler.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

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

    public static Placelist empty() {
        return new Placelist("");
    }

    public boolean isNew() {
        return id == null;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Placelist placelist = (Placelist) o;
        return Objects.equals(id, placelist.id) &&
                Objects.equals(name, placelist.name) &&
                Objects.equals(showOnMap, placelist.showOnMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, showOnMap);
    }



}
