package com.example.newzz;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newzz.database.AppDatabase;
import com.example.newzz.database.News;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<News>> news;

    public MainViewModel( Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        news = database.newsDao().loadAllFavorites();
    }

    public LiveData<List<News>> getTasks(){return news;}
}