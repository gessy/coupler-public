package com.offgrid.coupler.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.offgrid.coupler.data.converter.ChatTypeConverter;
import com.offgrid.coupler.data.converter.DateConverter;
import com.offgrid.coupler.data.dao.ChatDao;
import com.offgrid.coupler.data.dao.GroupDao;
import com.offgrid.coupler.data.dao.MessageDao;
import com.offgrid.coupler.data.dao.UserDao;
import com.offgrid.coupler.data.entity.Chat;
import com.offgrid.coupler.data.entity.Group;
import com.offgrid.coupler.data.entity.Message;
import com.offgrid.coupler.data.entity.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                Chat.class,
                Message.class,
                User.class,
                Group.class},
        version = 1,
        exportSchema = false
)
@TypeConverters({DateConverter.class, ChatTypeConverter.class})
public abstract class CouplerRoomDatabase extends RoomDatabase {

    public abstract ChatDao chatDao();

    public abstract MessageDao messageDao();

    public abstract UserDao userDao();

    public abstract GroupDao groupDao();


    private static volatile CouplerRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CouplerRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CouplerRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(
                                    context.getApplicationContext(),
                                    CouplerRoomDatabase.class, "coupler_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
//                    ChatDao chatDao = INSTANCE.chatDao();
//                    chatDao.deleteAll();
                }
            });
        }
    };
}
