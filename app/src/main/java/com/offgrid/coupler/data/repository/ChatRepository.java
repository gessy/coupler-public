package com.offgrid.coupler.data.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.offgrid.coupler.data.CouplerRoomDatabase;
import com.offgrid.coupler.data.dao.ChatDao;
import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.data.entity.ChatMessages;

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

    public LiveData<Chat> getChat(Long chatId) {
        return dao.findById(chatId);
    }

    public LiveData<ChatMessages> getChatMessages(Long chatId) {
        return dao.getChatMessages(chatId);
    }

    public LiveData<ChatMessages> getUserChatMessages(Long userId) {
        return dao.getUserChatMessages(userId);
    }

    public LiveData<Chat> getUserChat(Long userId) {
        return dao.findByUserId(userId);
    }

    public void insert(final Chat chat) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(chat);
            }
        });
    }

    public void update(final Chat chat) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.update(chat);
            }
        });
    }

    public void deleteChat(final long chat_id) {
        CouplerRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteChat(chat_id);
            }
        });
    }
}
