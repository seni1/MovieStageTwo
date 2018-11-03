package com.example.android.movieapponetestone.db;

import com.example.android.movieapponetestone.model.popular.Popular;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PopularDao {
    @Query("SELECT * FROM popular")
    LiveData<List<Popular>> getAll();

    @Insert
    void insert(Popular employee);

    @Update
    void update(Popular employee);

    @Delete
    void delete(Popular employee);

    @Insert
    void fab(Popular employee);
}
