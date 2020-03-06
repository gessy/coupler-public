package com.offgrid.coupler.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "T_Message")
public class Message {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Long id;

    @NonNull
    @ColumnInfo(name = "chat_id")
    private Long chatId;

    @NonNull
    @ColumnInfo(name = "message")
    private String message;

    @NonNull
    @ColumnInfo(name = "date")
    private Long date;

    @NonNull
    @ColumnInfo(name = "user_full_name")
    private String userFullName;

    @NonNull
    @ColumnInfo(name = "is_mine")
    private boolean isMine;


    public Message() {
    }

    public static Message getEmpty() {
        return new Message(0L, "Empty Message");
    }

    @Ignore
    public Message(@NonNull Long chatId, @NonNull String message) {
        this.chatId = chatId;
        this.message = message;
        this.userFullName = "No Name";
        this.date = System.currentTimeMillis();
    }

    @Ignore
    public Message(@NonNull Long chatId,
                   @NonNull String message,
                   @NonNull Long date,
                   @NonNull String userFullName,
                   boolean isMine) {
        this.chatId = chatId;
        this.message = message;
        this.date = date;
        this.userFullName = userFullName;
        this.isMine = isMine;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    @NonNull
    public Long getChatId() {
        return chatId;
    }

    public void setChatId(@NonNull Long chatId) {
        this.chatId = chatId;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    public void setMessage(@NonNull String message) {
        this.message = message;
    }

    @NonNull
    public Long getDate() {
        return date;
    }

    public void setDate(@NonNull Long date) {
        this.date = date;
    }

    @NonNull
    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(@NonNull String userFullName) {
        this.userFullName = userFullName;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }
}
