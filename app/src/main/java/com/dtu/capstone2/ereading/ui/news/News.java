package com.dtu.capstone2.ereading.ui.news;

public class News {

    private String title;
    private int  thumbnail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public News(String title, int thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }

}
