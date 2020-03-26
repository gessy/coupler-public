package com.offgrid.coupler.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.offgrid.coupler.data.entity.Message;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("select * from T_Message where chat_id = :chat_id")
    LiveData<List<Message>> findByChatId(long chat_id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Message message);

    @Query("delete from T_Message where chat_id = :chat_id")
    void deleteChatMessages(long chat_id);

}
