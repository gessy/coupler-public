package com.offgrid.coupler.data.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.offgrid.coupler.data.CouplerRoomDatabase;
import com.offgrid.coupler.data.dao.MessageDao;
import com.offgrid.coupler.data.entity.Message;

import java.util.List;

public class MessageRepository {

    private MessageDao dao;

    public MessageRepository(Application application) {
        CouplerRoomDatabase db = CouplerRoomDatabase.getDatabase(application);
        dao = db.messageDao();
    }

    public LiveData<List<Message>> getChatMessages(long chat_id) {
        return dao.findByChatId(chat_id);
    }


    public void deleteChatMessages(final long chat_id) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteChatMessages(chat_id);
            }
        });
    }


    public void insert(final Message message) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(message);
            }
        });
    }
}
