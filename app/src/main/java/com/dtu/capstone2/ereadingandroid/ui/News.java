package com.dtu.capstone2.ereadingandroid.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtu.capstone2.ereadingandroid.R;

/**
 * Create By Huynh Vu Ha Lan on 19/03/2019
 */
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

    public News(){

    }

}
