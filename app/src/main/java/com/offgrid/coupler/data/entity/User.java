package com.offgrid.coupler.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.offgrid.coupler.util.RandomLocation;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "T_User")
public class User {

    private static final String ZERO_GID = "000-000-000-000";

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Long id;

    @NonNull
    @ColumnInfo(name = "first_name")
    private String firstName;

    @NonNull
    @ColumnInfo(name = "last_name")
    private String lastName;

    @NonNull
    @ColumnInfo(name = "gid")
    private String gid;

    @NonNull
    @ColumnInfo(name = "allow_notify")
    private boolean allowNotify;

    @NonNull
    @ColumnInfo(name = "last_seen")
    private Date lastSeen;

    @ColumnInfo(name = "live_latitude")
    public double liveLatitude;

    @ColumnInfo(name = "live_longitude")
    public double liveLongitude;


    public User() {
    }

    public static User getEmpty() {
        return new User("No", "Name", ZERO_GID);
    }


    public boolean isEmpty() {
        return gid.equals(ZERO_GID);
    }


    @Ignore
    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String gid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gid = gid;
        this.allowNotify = true;
        this.lastSeen = new Date();
        setLocation(RandomLocation.get());
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    public String getGid() {
        return gid;
    }

    public void setGid(@NonNull String gid) {
        this.gid = gid;
    }

    public boolean isAllowNotify() {
        return allowNotify;
    }

    public void setAllowNotify(boolean allowNotify) {
        this.allowNotify = allowNotify;
    }

    @NonNull
    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(@NonNull Date lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String chatTitle() {
        return fullName();
    }

    public String fullName() {
        return firstName + " " + lastName;
    }

    public LatLng getLocation() {
        return new LatLng(liveLatitude, liveLongitude);
    }

    public void setLocation(LatLng latLng) {
        liveLatitude = latLng.getLatitude();
        liveLongitude = latLng.getLongitude();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(gid, user.gid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gid, firstName, lastName);
    }

}
