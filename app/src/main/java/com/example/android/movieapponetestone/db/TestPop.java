package com.example.android.movieapponetestone.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TestPop {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
