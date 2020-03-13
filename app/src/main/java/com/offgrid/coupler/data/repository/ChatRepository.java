package com.offgrid.coupler.data.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.offgrid.coupler.data.CouplerRoomDatabase;
import com.offgrid.coupler.data.dao.ChatDao;
import com.offgrid.coupler.data.entity.Chat;

import java.util.List;

public class ChatRepository {

    private ChatDao dao;

    public ChatRepository(Application application) {
        CouplerRoomDatabase db = CouplerRoomDatabase.getDatabase(application);
        dao = db.chatDao();
    }

    public LiveData<List<Chat>> getChats() {
        return dao.findAll();
    }

    public LiveData<Chat> getChat(long chatId) {
        return dao.findById(chatId);
    }

    public void insert(final Chat chat) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(chat);
            }
        });
    }
}
