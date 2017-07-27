package com.example.christopherfong.newsapp.Model;

import android.provider.BaseColumns;

/**
 * Created by christopherfong on 7/25/17.
 */

public class Contract {

    public static class TABLE_ARTICLES implements BaseColumns {
        public static final String TABLE_NAME = "articles";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_PUBLISHED_DATE = "published_date";
        public static final String COLUMN_NAME_ABSTRACT = "abstract";
        public static final String COLUMN_NAME_THUMBURL = "thumb_url";
        public static final String COLUMN_NAME_URL = "url";
    }

}
