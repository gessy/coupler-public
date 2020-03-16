package com.offgrid.coupler.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "T_User")
public class User {
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
    private Long lastSeen;


    public User() {
    }

    public static User getEmpty() {
        return new User("No", "Name", "000-000-000-000");
    }

    @Ignore
    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String gid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gid = gid;
        this.allowNotify = true;
        this.lastSeen = System.currentTimeMillis();
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
    public Long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(@NonNull Long lastSeen) {
        this.lastSeen = lastSeen;
    }
}
