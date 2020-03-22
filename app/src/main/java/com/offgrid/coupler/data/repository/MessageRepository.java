package com.offgrid.coupler.data.repository;


import android.app.Application;

import com.offgrid.coupler.data.CouplerRoomDatabase;
import com.offgrid.coupler.data.dao.MessageDao;
import com.offgrid.coupler.data.entity.Message;


public class MessageRepository {

    private MessageDao dao;

    public MessageRepository(Application application) {
        CouplerRoomDatabase db = CouplerRoomDatabase.getDatabase(application);
        dao = db.messageDao();
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
