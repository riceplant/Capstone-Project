package com.riceplant.capstoneproject.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {MyGame.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class GameRoomDatabase extends RoomDatabase {
    private static final String LOG_TAG = GameRoomDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "gameslist";
    private static GameRoomDatabase sInstance;

    public static GameRoomDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        GameRoomDatabase.class, GameRoomDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract GameDao gameDao();
}
