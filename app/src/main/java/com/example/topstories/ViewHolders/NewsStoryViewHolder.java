package com.example.topstories.ViewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.topstories.R;

public class NewsStoryViewHolder extends RecyclerView.ViewHolder {
    public ImageView newsImage;
    public TextView newsTitle;
    public TextView newsLink;

    public NewsStoryViewHolder(@NonNull View itemView) {
        super(itemView);
        newsImage = itemView.findViewById(R.id.newsImage);
        newsTitle = itemView.findViewById(R.id.newsTitle);
        newsLink = itemView.findViewById(R.id.newsLink);
    }
}
