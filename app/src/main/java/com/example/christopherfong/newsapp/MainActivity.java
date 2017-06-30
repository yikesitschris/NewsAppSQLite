package com.example.christopherfong.newsapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.christopherfong.newsapp.Model.NewsItem;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "mainactivity";

    private RecyclerView rView;
    private ProgressBar spinning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rView = (RecyclerView) findViewById(R.id.recyclerView);
        spinning = (ProgressBar) findViewById(R.id.progressBar);

        rView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber = item.getItemId();
        if (itemNumber == R.id.action_search) {
            NetworkTask task = new NetworkTask();
            task.execute();
        }
        return true;
    }

    class NetworkTask extends AsyncTask<URL, Void, ArrayList<NewsItem>> {

        String query = "the-next-web";
        String sortBy = "latest";
        //Enter api string here
        String api = "3d809ad78cdb446d8584b18e6401a5d9";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            spinning.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<NewsItem> doInBackground(URL... params) {
            ArrayList<NewsItem> newsList = null;
            URL url = NetworkUtils.makeURL(query, sortBy, api);
            Log.d(TAG, "url: " + url.toString());
            try {
                String json = NetworkUtils.getResponseFromHttpUrl(url);
                newsList = NetworkUtils.parseJSON(json);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return newsList;
        }

        @Override
        protected void onPostExecute(final ArrayList<NewsItem> data) {
            super.onPostExecute(data);
            spinning.setVisibility(View.GONE);
            if (data != null) {
                NewsItemAdapter adapter = new NewsItemAdapter(data, new NewsItemAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(int clickedItemIndex) {
                        String url = data.get(clickedItemIndex).getUrl();
                        Log.d(TAG, String.format("Url %s", url));
                    }
                });
                rView.setAdapter(adapter);

            }
        }
    }

}

