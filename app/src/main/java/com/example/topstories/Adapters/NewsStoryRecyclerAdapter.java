package com.example.topstories.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.topstories.Activities.NewsStoryDetailsActivity;
import com.example.topstories.Models.NewsStory;
import com.example.topstories.R;
import com.example.topstories.ViewHolders.NewsStoryViewHolder;

import java.util.ArrayList;

public class NewsStoryRecyclerAdapter extends RecyclerView.Adapter<NewsStoryViewHolder> implements View.OnClickListener {
    private ArrayList<NewsStory> newsStories = new ArrayList<>();
    @NonNull
    @Override
    public NewsStoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        NewsStoryViewHolder newsStoryViewHolder = new NewsStoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_story_item, parent, false));
        return newsStoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsStoryViewHolder viewHolder, int position) {
        NewsStory newsStory = newsStories.get(position);
        viewHolder.newsTitle.setText(newsStory.getTitle());
        viewHolder.newsLink.setText(newsStory.getShortUrl());
        Glide.with(viewHolder.itemView.getContext()).load(newsStory.getThumbnail()).into(viewHolder.newsImage);
        viewHolder.itemView.setTag(viewHolder);
        viewHolder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return newsStories.size();
    }

    public void updateNewsStories(ArrayList<NewsStory> updatedNewsStories) {
        newsStories.clear();
        newsStories.addAll(updatedNewsStories);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.rootLayout:
                Object tag = v.getTag();
                if(tag != null && tag instanceof NewsStoryViewHolder) {
                    NewsStoryViewHolder viewHolder = (NewsStoryViewHolder) tag;
                    NewsStory story = newsStories.get(viewHolder.getAdapterPosition());
                    Intent intent = new Intent(v.getContext(), NewsStoryDetailsActivity.class);
                    intent.putExtra(NewsStoryDetailsActivity.BACKGROUNDIMAGEURL, story.getThumbnail());
                    intent.putExtra(NewsStoryDetailsActivity.DESCRIPTION, story.getDescription());
                    intent.putExtra(NewsStoryDetailsActivity.URL, story.getShortUrl());
                    //Handles shared layout animation
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) viewHolder.itemView.getContext(), viewHolder.newsImage, "newsTransition");
                    viewHolder.itemView.getContext().startActivity(intent, options.toBundle());
                }
                break;
        }
    }
}
