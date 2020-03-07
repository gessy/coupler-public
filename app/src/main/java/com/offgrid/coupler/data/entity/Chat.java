package com.offgrid.coupler.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.offgrid.coupler.data.model.ChatType;

@Entity(tableName = "T_Chat")
public class Chat {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Long id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "last_message")
    private String lastMessage;

    @NonNull
    @ColumnInfo(name = "type")
    private String type;


    public static Chat getEmpty() {
        return new Chat("No data","No data");
    }

    public Chat() {
    }

    @Ignore
    public Chat(@NonNull String title, @NonNull String message) {
        this.title = title;
        this.lastMessage = message;
        this.type = ChatType.PERSONAL.toString();
    }

    @Ignore
    public Chat(@NonNull String title, @NonNull String message, @NonNull String type) {
        this.title = title;
        this.lastMessage = message;
        this.type = type;
    }


    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(@NonNull String lastMessage) {
        this.lastMessage = lastMessage;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }
}
