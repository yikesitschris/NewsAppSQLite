package com.example.christopherfong.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.christopherfong.newsapp.Model.NewsItem;

import java.util.ArrayList;

/**
 * Created by christopherfong on 6/28/17.
 */

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.ItemHolder> {

    private ArrayList<NewsItem> newsItems;
    ItemClickListener listener;
    private String s;

    public NewsItemAdapter(ArrayList<NewsItem> newsItems, ItemClickListener listener) {
        this.newsItems = newsItems;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(int clickedItemIndex);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.news_list_item, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        TextView time;


        ItemHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.item_title);
            description = (TextView) view.findViewById(R.id.item_description);
            time = (TextView) view.findViewById(R.id.item_time);
            view.setOnClickListener(this);
        }

        public void bind(int pos) {
            NewsItem ni = newsItems.get(pos);
            title.setText("Title: \n" + ni.getTitle() + "\n");
            description.setText("Description: \n" + ni.getDescription() + "\n");
            time.setText("Time: \n" + ni.getPublishedAt());
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos);
        }
    }


}
