package com.example.musicplayer;

import android.content.Context;
import android.provider.CalendarContract;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities ={SongsEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public  abstract  DAO dao();

    public static synchronized AppDatabase getInstance(Context context) {
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,"songs_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
