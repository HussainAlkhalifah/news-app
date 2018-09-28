package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by husbb on 5/14/2018.
 */

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {
    private String url;

    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<News> loadInBackground() {
        if (url == null) {
            Log.e(MainActivity.class.getName(), "URL is Empty");
            return null;
        }
        ArrayList<News> news = NetworkTask.fetchNewsData(url);
        return news;
    }
}
