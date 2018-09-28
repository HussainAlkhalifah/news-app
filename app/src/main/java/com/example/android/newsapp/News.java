package com.example.android.newsapp;

/**
 * Created by husbb on 5/14/2018.
 */

public class News {
    private String sectionName;
    private String webTitle;
    private String webPublicationDate;
    private String webUrl;
    private String author;

    public News(String sectionName, String webTitle, String webPublicationDate, String webUrl, String author) {
        this.sectionName = sectionName;
        this.webTitle = webTitle;
        this.webPublicationDate = webPublicationDate;
        this.webUrl = webUrl;
        this.author = author;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getAuthor() {
        return author;
    }
}