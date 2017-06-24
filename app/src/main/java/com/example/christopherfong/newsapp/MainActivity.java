package com.example.christopherfong.newsapp;

import android.net.Network;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "mainactivity";

    private TextView mJSONTextView;
    private ProgressBar spinning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJSONTextView = (TextView) findViewById(R.id.displayJSON);
        spinning = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber = item.getItemId();
        if(itemNumber == R.id.action_search) {
            NetworkTask task = new NetworkTask();
            task.execute();
        }
        return true;
    }


    class NetworkTask extends AsyncTask<URL, Void, String> {

        String query = "the-next-web";
        String sortBy = "latest";
        //Enter api string here
        String api = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            spinning.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            String result = null;
            URL url = NetworkUtils.makeURL(query, sortBy, api);
            Log.d(TAG, "url: " + url.toString());
            try {
                result = NetworkUtils.getResponseFromHttpUrl(url);
            }catch(IOException e){
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            mJSONTextView.setText(s);
            spinning.setVisibility(View.GONE);
        }
    }


}
