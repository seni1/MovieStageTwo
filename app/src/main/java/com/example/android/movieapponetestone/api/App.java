package com.example.android.movieapponetestone.api;

import android.app.Application;

import com.example.android.movieapponetestone.db.AppDatabase;

import androidx.room.Room;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static MovieApi api;
    private Retrofit retrofit;

    private static final String DATABASE_NAME = "database";
    private static App instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(MovieApi.class);

        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
    }

    public static MovieApi getApi() {
        return api;
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
