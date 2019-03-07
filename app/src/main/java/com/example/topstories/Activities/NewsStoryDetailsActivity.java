package com.example.topstories.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.topstories.R;

public class NewsStoryDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String BACKGROUNDIMAGEURL = "BACKGROUNDIMAGEURL";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String URL = "URL";
    String backgroundImageUrl;
    String description;
    String url;

    ImageView newsImage;
    TextView newsDescription;
    FloatingActionButton shareButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_story_details);
        //Wait for image to load before starting
        supportPostponeEnterTransition();

        //adds back button to activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        newsImage = findViewById(R.id.newsImage);
        newsDescription = findViewById(R.id.newsDescription);
        shareButton = findViewById(R.id.shareButton);
        if(getIntent() != null && getIntent().getExtras() != null) {
            backgroundImageUrl = getIntent().getExtras().getString(BACKGROUNDIMAGEURL);
            description = getIntent().getExtras().getString(DESCRIPTION);
            url = getIntent().getExtras().getString(URL);
        }

        shareButton.setOnClickListener(this);
        newsDescription.setText(description);

        Glide.with(getApplicationContext()).load(backgroundImageUrl).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                //image loaded start transition
                supportStartPostponedEnterTransition();
                return false;
            }
        }).into(newsImage);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shareButton:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text, url));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
