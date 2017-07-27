package com.example.christopherfong.newsapp;

import android.net.Uri;
import android.util.Log;

import com.example.christopherfong.newsapp.Model.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by christopherfong on 6/21/17.
 */

public class NetworkUtils {

    final static String TAG = NetworkUtils.class.getSimpleName();

    public static final String GITHUB_BASE_URL =
            "https://newsapi.org/v1/articles";
    public static final String PARAM_QUERY = "source";
    public static final String PARAM_SORT = "sortBy";
    public static final String PARAM_API_KEY = "apiKey";

    public static ArrayList<NewsItem> parseJSON(String s) throws JSONException {
        ArrayList<NewsItem> newsList = new ArrayList<>();
        JSONObject main = new JSONObject(s);
        JSONArray articles = main.getJSONArray("articles");

        for (int i = 0; i < articles.length(); i++) {
            JSONObject article = articles.getJSONObject(i);

            String title = article.getString("title");
            String pubDate = article.getString("publishedAt");
            String abstr = article.getString("description");
            String url = article.getString("url");
            String imgUrl = article.getString("urlToImage");


            NewsItem newsItem = new NewsItem(title, pubDate, abstr, imgUrl, url);
            newsList.add(newsItem);

        }
        return newsList;
    }

    public static URL makeURL() {
        Uri uri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, "the-next-web")
                .appendQueryParameter(PARAM_SORT, "latest")
                .appendQueryParameter(PARAM_API_KEY, "3d809ad78cdb446d8584b18e6401a5d9").build();

        URL url = null;
        try {
            String urlString = uri.toString();
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "url:" + url);
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
