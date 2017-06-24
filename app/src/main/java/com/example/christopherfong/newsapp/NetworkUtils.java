package com.example.christopherfong.newsapp;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

        try{
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
