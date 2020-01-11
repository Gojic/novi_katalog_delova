package com.example.novikatalogdelova.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.novikatalogdelova.R;
import com.example.novikatalogdelova.model.Deo;

@Database(entities = {Deo.class},version = 1)
public abstract class DeoDataBase extends RoomDatabase {
    public abstract DeoDAO deoDAO();
    private static DeoDataBase instance;

    public static synchronized DeoDataBase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), DeoDataBase.class, "note_table")
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return instance;
    }

    }

