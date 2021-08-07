package com.example.newzz.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news")
    LiveData<List<News>> loadAllFavorites();

    @Insert
    void insertNews(News news);

    @Delete
    void deleteNews(News news);

}
