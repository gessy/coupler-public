package com.offgrid.coupler.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.offgrid.coupler.data.model.ChatType;
import com.offgrid.coupler.util.RandomTokenGenerator;

import static java.lang.System.currentTimeMillis;

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

    @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id")
    @ColumnInfo(name = "user_id")
    private Long userId;

    @ForeignKey(entity = Group.class, parentColumns = "id", childColumns = "group_id")
    @ColumnInfo(name = "group_id")
    private Long groupId;

    @ColumnInfo(name = "last_modification_date")
    private Long lastModificationDate;

    @NonNull
    @ColumnInfo(name = "visible")
    private boolean visible;


    public Chat() {
    }

    @Ignore
    private Chat(@NonNull String title,
                 @NonNull String lastMessage,
                 @NonNull String type,
                 Long userId,
                 Long groupId,
                 boolean visible) {
        this.title = title;
        this.lastMessage = lastMessage;
        this.type = type;
        this.userId = userId;
        this.groupId = groupId;
        this.lastModificationDate = currentTimeMillis();
        this.visible = visible;
    }


    public static Chat getEmpty() {
        return new Chat("No data", "No data", ChatType.PERSONAL.toString(), null, null, true);
    }

    public static Chat personalChat(@NonNull String title, Long userId) {
        return new Chat(title, "", ChatType.PERSONAL.toString(), userId, null, false);
    }

    public static Chat groupChat(@NonNull String title) {
        return new Chat(title, "", ChatType.GROUP.toString(), null, null, true);
    }


    public static Chat groupChat(@NonNull String title, Long groupId) {
        return new Chat(title, "", ChatType.GROUP.toString(), null, groupId, true);
    }

    public static Chat randGroupChat() {
        int id = RandomTokenGenerator.getRandInt();
        String title = "Chat ID " + id;
        String message = "Last message on Chat ID " + id;
        return new Chat(title, message, ChatType.GROUP.toString(), null, null, true);
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Long lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
