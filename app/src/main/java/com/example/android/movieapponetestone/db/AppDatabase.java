package com.example.android.movieapponetestone.db;

import com.example.android.movieapponetestone.model.popular.Popular;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Popular.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PopularDao popularDao();
}

