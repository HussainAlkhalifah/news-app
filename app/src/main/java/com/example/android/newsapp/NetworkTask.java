package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by husbb on 5/14/2018.
 */

public class NetworkTask {
    public static URL createUrl(String strUrl) {
        URL url = null;
        try {
            url = new URL(strUrl);
        } catch (MalformedURLException e) {
            e.getStackTrace();
        }
        return url;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String json = "";
        if (url == null) {
            return json;
        }

        HttpURLConnection connection = null;
        InputStream inStream = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000 /* milliseconds */);
            connection.setConnectTimeout(15000 /* milliseconds */);
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == 200) {
                inStream = connection.getInputStream();
                json = readFromStream(inStream);
            } else {
                Log.e(MainActivity.class.getName(), "Error response code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(MainActivity.class.getName(), "Problem with news JSON results.", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inStream != null) {
                inStream.close();
            }
        }
        return json;
    }

    private static String readFromStream(InputStream inStream) throws IOException {
        StringBuilder strOut = new StringBuilder();
        if (inStream != null) {
            InputStreamReader inReader = new InputStreamReader(inStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inReader);
            String line = reader.readLine();
            while (line != null) {
                strOut.append(line);
                line = reader.readLine();
            }
        }
        return strOut.toString();
    }

    public static ArrayList<News> extractResponseFromJson(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        ArrayList<News> news = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(newsJSON);
            JSONObject responseObject = root.getJSONObject("response");
            JSONArray resultArray = responseObject.getJSONArray("results");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject curNews = resultArray.getJSONObject(i);
                String title = curNews.getString("webTitle");
                String date = curNews.getString("webPublicationDate");
                String section = curNews.getString("sectionName");
                String webUrl = curNews.getString("webUrl");
                String author = null;
                JSONArray tagsArray = curNews.getJSONArray("tags");
                for (int j = 0; j < tagsArray.length(); j++) {
                    JSONObject curTag = tagsArray.getJSONObject(j);
                    String firstName = curTag.getString("firstName");
                    String lastName = curTag.getString("lastName");
                    author = firstName + " " + lastName;
                }
                News newsItem = new News(section, title, date, webUrl, author);
                news.add(newsItem);
            }

        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }
        return news;
    }

    public static ArrayList<News> fetchNewsData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String json = null;
        try {
            json = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(MainActivity.class.getName(), "Problem making the HTTP request.", e);
        }
        return extractResponseFromJson(json);
    }
}