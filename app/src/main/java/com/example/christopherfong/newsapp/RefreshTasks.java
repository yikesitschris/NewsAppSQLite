package com.example.christopherfong.newsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.example.christopherfong.newsapp.Model.NewsItem;
import com.example.christopherfong.newsapp.Model.DBHelper;
import com.example.christopherfong.newsapp.Model.DBUtil;

/**
 * Created by christopherfong on 7/25/17.
 */

public class RefreshTasks {

    public static final String ACTION_REFRESH = "refresh";


    public static void refreshArticles(Context context) {
        ArrayList<NewsItem> result = null;
        URL url = NetworkUtils.makeURL();

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        try {
            DBUtil.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            result = NetworkUtils.parseJSON(json);
            DBUtil.bulkInsert(db, result);

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.close();
    }

}
