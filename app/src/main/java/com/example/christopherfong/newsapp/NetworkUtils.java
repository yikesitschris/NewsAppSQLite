package com.example.christopherfong.newsapp;

import android.net.Uri;

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

    public static final String GITHUB_BASE_URL =
            "https://newsapi.org/v1/articles";
    public static final String PARAM_QUERY = "source";
    public static final String PARAM_SORT = "sortBy";
    public static final String PARAM_API_KEY = "apiKey";

    public static ArrayList<NewsItem> parseJSON(String s) throws JSONException {
        ArrayList<NewsItem> newsList = new ArrayList<>();
        JSONObject main = new JSONObject(s);
        JSONArray articles = main.getJSONArray("articles");

        for (int i = 0; i < newsList.size(); i++) {
            JSONObject article = articles.getJSONObject(i);
            String author = article.getString("author");
            String title = article.getString("title");
            String description = article.getString("description");
            String url = article.getString("url");
            String urlToImage = article.getString("urlToImage");
            String time = article.getString("publishedAt");
            NewsItem newsItem = new NewsItem(author, title, description, url, urlToImage, time);
            newsList.add(newsItem);

        }
        return newsList;
    }

    public static URL makeURL(String searchQuery, String sortBy, String apiKey) {
        Uri uri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, searchQuery)
                .appendQueryParameter(PARAM_SORT, sortBy)
                .appendQueryParameter(PARAM_API_KEY, apiKey).build();

        URL url = null;
        try {
            String urlString = uri.toString();
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

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
