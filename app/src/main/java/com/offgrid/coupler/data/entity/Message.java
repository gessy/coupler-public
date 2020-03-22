package com.offgrid.coupler.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import static com.offgrid.coupler.util.RandomTokenGenerator.getRandInt;
import static java.lang.System.currentTimeMillis;

@Entity(tableName = "T_Message")
public class Message {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Long id;

    @ForeignKey(entity = Chat.class, parentColumns = "id", childColumns = "chat_id")
    @NonNull
    @ColumnInfo(name = "chat_id")
    private Long chatId;

    @NonNull
    @ColumnInfo(name = "message")
    private String message;

    @NonNull
    @ColumnInfo(name = "date")
    private Date date;

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
        this.date = new Date();
    }

    @Ignore
    public Message(@NonNull Long chatId,
                   @NonNull String message,
                   @NonNull Date date,
                   @NonNull String userFullName,
                   boolean isMine) {
        this.chatId = chatId;
        this.message = message;
        this.date = date;
        this.userFullName = userFullName;
        this.isMine = isMine;
    }


    @Ignore
    private Message(@NonNull String message,
                    @NonNull Date date,
                    @NonNull String userFullName,
                    boolean isMine) {
        this.message = message;
        this.date = date;
        this.userFullName = userFullName;
        this.isMine = isMine;
    }


    public static Message talkerMessage() {
        String message = "Message ID " + getRandInt() + " How are you doing? This is a long message that should probably wrap.";
        return new Message(message, new Date(), "Talker", false);
    }


    public static Message myMessage(String message) {
        return new Message(message, new Date(), "Me", true);
    }

    public static Message myMessage(String message, Long chatId) {
        return new Message(chatId, message, new Date(), "Me", true);
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
    public Date getDate() {
        return date;
    }

    public void setDate(@NonNull Date date) {
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
