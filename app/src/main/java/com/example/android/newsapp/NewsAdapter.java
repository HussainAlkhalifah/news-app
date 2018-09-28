package com.example.android.newsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by husbb on 5/14/2018.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Activity context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View newsListView = convertView;
        if (newsListView == null) {
            newsListView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        News currentNews = getItem(position);
        TextView titleTextView = (TextView) newsListView.findViewById(R.id.tiitle);
        titleTextView.setText(currentNews.getWebTitle());
        TextView sectionTextView = (TextView) newsListView.findViewById(R.id.section);
        sectionTextView.setText(currentNews.getSectionName());
        TextView dateTextView = (TextView) newsListView.findViewById(R.id.date);
        dateTextView.setText(currentNews.getWebPublicationDate());
        TextView authorTextView = (TextView) newsListView.findViewById(R.id.author);
        authorTextView.setText(currentNews.getAuthor());
        return newsListView;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
}
