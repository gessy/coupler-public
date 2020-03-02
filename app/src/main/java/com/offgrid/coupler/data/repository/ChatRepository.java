package com.offgrid.coupler.data.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.offgrid.coupler.data.CouplerRoomDatabase;
import com.offgrid.coupler.data.dao.ChatDao;
import com.offgrid.coupler.data.domain.Chat;

import java.util.List;

public class ChatRepository {

    private ChatDao chatDao;
    private LiveData<List<Chat>> chats;

    public ChatRepository(Application application) {
        CouplerRoomDatabase db = CouplerRoomDatabase.getDatabase(application);
        chatDao = db.chatDao();
        chats = chatDao.findAll();
    }

    public LiveData<List<Chat>> getAllChats() {
        return chats;
    }

    public void insert(final Chat chat) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                chatDao.insert(chat);
            }
        });
    }
}
