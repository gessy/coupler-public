package com.offgrid.coupler.data.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.offgrid.coupler.util.RandomTokenGenerator;

@Entity(tableName = "T_Chat")
public class Chat {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Integer id;

    @NonNull
    @ColumnInfo(name = "uuid")
    private String uuid;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "message")
    private String message;

    @NonNull
    @ColumnInfo(name = "date")
    private Integer date;


    public static Chat getEmpty() {
        return new Chat("No data","No data",0);
    }

    public Chat() {
    }

    public Chat(@NonNull String title) {
        this.uuid = RandomTokenGenerator.get();
        this.title = title;
    }

    public Chat(@NonNull String title, @NonNull String message, @NonNull Integer date) {
        this.uuid = RandomTokenGenerator.get();
        this.title = title;
        this.message = message;
        this.date = date;
    }


    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public String getUuid() {
        return uuid;
    }

    public void setUuid(@NonNull String uuid) {
        this.uuid = uuid;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    public void setMessage(@NonNull String message) {
        this.message = message;
    }

    @NonNull
    public Integer getDate() {
        return date;
    }

    public void setDate(@NonNull Integer date) {
        this.date = date;
    }
}
