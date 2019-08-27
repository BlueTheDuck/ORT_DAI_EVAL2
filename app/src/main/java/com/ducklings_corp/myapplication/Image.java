package com.ducklings_corp.myapplication;


import android.util.Log;

public class Image {
    String urlStr;
    String title;
    Image(String url,String title) {
        this.urlStr = url;
        this.title = title;
        Log.d("image","("+this.urlStr+";"+this.title+")");
    }
}
