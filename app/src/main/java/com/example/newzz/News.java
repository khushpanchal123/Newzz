package com.example.newzz;

public class News {

    private final String murl;
    private final String mimage;
    private final String mtitle;

    public News(String image, String title, String url){
        mimage = image;
        mtitle = title;
        murl = url;
    }

    public String getUrl(){return murl;}
    public String getImage(){return mimage;}
    public String getTitle(){return mtitle;}

}
