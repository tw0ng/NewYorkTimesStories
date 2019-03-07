package com.example.topstories.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.topstories.Adapters.NewsStoryRecyclerAdapter;
import com.example.topstories.Models.NewsStory;
import com.example.topstories.R;
import com.example.topstories.Util.NewsStoryUtil;
import com.example.topstories.Views.NewsStoryItemDecoration;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView newsStoriesRecycler;
    private NewsStoryRecyclerAdapter newsStoryRecyclerAdapter;
    private LinearLayoutManager newsStoryLinearLayoutManager;
    private TextView errorTextView;

    Response.Listener<JSONObject> newsStoryResponseListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            ArrayList<NewsStory> newsStories = NewsStoryUtil.getNewsStories(response);
            updateNewsStories(newsStories);
            if(newsStories.size() == 0) {
                errorTextView.setVisibility(View.VISIBLE);
            } else {
                errorTextView.setVisibility(View.GONE);
            }
        }
    };

    Response.ErrorListener newsStoryErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            updateNewsStories(new ArrayList<NewsStory>());
            errorTextView.setVisibility(View.VISIBLE);
        }
    };

    SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            if(TextUtils.isEmpty(s)) {
                NewsStoryUtil.startNewsStoryRequest(getApplicationContext(), NewsStoryUtil.DEFAULT_SEARCH, newsStoryResponseListener, newsStoryErrorListener);
            } else {
                NewsStoryUtil.startNewsStoryRequest(getApplicationContext(), s, newsStoryResponseListener, newsStoryErrorListener);
            }
            return true;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            if(TextUtils.isEmpty(s) && newsStoryRecyclerAdapter.getItemCount() == 0) {
                NewsStoryUtil.startNewsStoryRequest(getApplicationContext(), NewsStoryUtil.DEFAULT_SEARCH, newsStoryResponseListener, newsStoryErrorListener);
            }
            return true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorTextView = findViewById(R.id.errorTextView);
        newsStoriesRecycler = findViewById(R.id.newsStoriesRecycler);
        NewsStoryItemDecoration newsStoryItemDecoration = new NewsStoryItemDecoration();
        newsStoriesRecycler.addItemDecoration(newsStoryItemDecoration);
        newsStoryLinearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        newsStoriesRecycler.setLayoutManager(newsStoryLinearLayoutManager);
        newsStoryRecyclerAdapter = new NewsStoryRecyclerAdapter();
        newsStoriesRecycler.setAdapter(newsStoryRecyclerAdapter);
        NewsStoryUtil.startNewsStoryRequest(getApplicationContext(), NewsStoryUtil.DEFAULT_SEARCH, newsStoryResponseListener, newsStoryErrorListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(queryTextListener);

        return true;
    }

    //Handles updating of the recycler and starting animations
    private void updateNewsStories(ArrayList<NewsStory> newsStories) {
        newsStoryRecyclerAdapter.updateNewsStories(newsStories);
        newsStoryRecyclerAdapter.notifyDataSetChanged();
        newsStoriesRecycler.scheduleLayoutAnimation();
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }
}
