package com.offgrid.coupler.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.offgrid.coupler.data.model.ChatType;

import java.util.Date;

import static com.offgrid.coupler.data.model.ChatType.GROUP;
import static com.offgrid.coupler.data.model.ChatType.PERSONAL;


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
    @ColumnInfo(name = "is_mine_last_message")
    private boolean isMineLastMessage;

    @NonNull
    @ColumnInfo(name = "type")
    private ChatType type;

    @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id")
    @ColumnInfo(name = "user_id")
    private Long userId;

    @ForeignKey(entity = Group.class, parentColumns = "id", childColumns = "group_id")
    @ColumnInfo(name = "group_id")
    private Long groupId;

    @ColumnInfo(name = "last_modification_date")
    private Date lastModificationDate;

    @NonNull
    @ColumnInfo(name = "visible")
    private boolean visible;


    public Chat() {
    }

    @Ignore
    private Chat(@NonNull String title,
                 @NonNull String lastMessage,
                 @NonNull ChatType type,
                 Long userId,
                 Long groupId,
                 boolean visible) {
        this.title = title;
        this.lastMessage = lastMessage;
        this.isMineLastMessage = false;
        this.type = type;
        this.userId = userId;
        this.groupId = groupId;
        this.lastModificationDate = new Date();
        this.visible = visible;
    }


    public static Chat getEmpty() {
        return new Chat("No data", "No data", PERSONAL, null, null, true);
    }

    public static Chat personalChat(@NonNull String title) {
        return new Chat(title, "", PERSONAL, null, null, false);
    }

    public static Chat groupChat(@NonNull String title) {
        return new Chat(title, "", GROUP, null, null, true);
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
    public ChatType getType() {
        return type;
    }

    public void setType(@NonNull ChatType type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
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


    public boolean isMineLastMessage() {
        return isMineLastMessage;
    }

    public void setMineLastMessage(boolean mineLastMessage) {
        isMineLastMessage = mineLastMessage;
    }
}
