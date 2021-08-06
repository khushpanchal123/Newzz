package com.example.newzz.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "news")
public class News {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String url;
    private final String image;
    private final String title;
    @Ignore
    private boolean favoriteStatus = false;

    @Ignore
    public News(String image, String title, String url){
        this.image = image;
        this.title = title;
        this.url = url;
    }

    public News(int id, String image, String title, String url){
        this.id = id;
        this.image = image;
        this.title = title;
        this.url = url;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public String getUrl(){return url;}
    public String getImage(){return image;}
    public String getTitle(){return title;}
    public boolean getFavoriteStatus() {return favoriteStatus;}

    public void setFavoriteStatus(boolean status) {this.favoriteStatus = status;}
}
