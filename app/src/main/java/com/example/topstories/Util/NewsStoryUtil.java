package com.example.topstories.Util;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.topstories.BuildConfig;
import com.example.topstories.Models.NewsStory;
import com.example.topstories.R;
import com.example.topstories.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsStoryUtil {
    public static final String NEWSSTORYREQUESTTAG = "NEWSSTORYREQUESTTAG";
    public static final String DEFAULT_SEARCH = "home";
    public static final String NY_RESULTS_TAG = "results";
    public static final String NEWS_STORY_TITLE_TAG = "title";
    public static final String NEWS_STORY_SHORTURL_TAG = "short_url";
    public static final String NEWS_STORY_REGULARURL_TAG = "url";
    public static final String NEWS_STORY_DESCRIPTION_TAG = "abstract";
    public static final String NEWS_STORY_IMAGES_TAG = "multimedia";
    public static final String NEWS_STORY_MULTIMEDIA_URL_TAG = "url";
    public static final String NEWS_STORY_MULTIMEDIA_TYPE_TAG = "type";
    public static final String NEWS_STORY_MULTIMEDIA_FORMAT_TAG = "format";
    public static final String NY_IMAGE_TYPE = "image";
    public static final String NY_FORMAT_THUMBNAIL_TYPE = "thumbLarge";
    public static final String NY_FORMAT_IMAGE_TYPE = "superJumbo";


    public static void startNewsStoryRequest(Context context, String searchTerm, Response.Listener responseListener, Response.ErrorListener errorListener) {
        //make sure only one request for story is going on at once
        Singleton.getInstance(context).getRequestQueue().cancelAll(NEWSSTORYREQUESTTAG);
        JsonObjectRequest newsStoryJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, context.getString(R.string.top_stories_url, searchTerm, BuildConfig.nytimes_api_key), null, responseListener, errorListener);
        newsStoryJsonObjectRequest.setTag(NEWSSTORYREQUESTTAG);
        Singleton.getInstance(context).addToRequestQueue(newsStoryJsonObjectRequest);
    }

    public static ArrayList<NewsStory> getNewsStories(JSONObject response) {
        ArrayList<NewsStory> newsStories = new ArrayList<>();
        try {
            if(response.has(NY_RESULTS_TAG)) {
                NewsStory newsStory;
                JSONObject article;
                JSONArray articleResults = response.getJSONArray(NY_RESULTS_TAG);
                for(int articleIndex = 0; articleIndex < articleResults.length(); articleIndex++) {
                    article = articleResults.getJSONObject(articleIndex);
                    newsStory = new NewsStory(article.optString(NEWS_STORY_TITLE_TAG), article.optString(NEWS_STORY_SHORTURL_TAG), article.optString(NEWS_STORY_DESCRIPTION_TAG));
                    //Checks for multimedia items
                    if(article.has(NEWS_STORY_IMAGES_TAG)) {
                        JSONArray multimedia = article.getJSONArray(NEWS_STORY_IMAGES_TAG);
                        for(int multimediaIndex = 0; multimediaIndex < multimedia.length(); multimediaIndex++) {
                            JSONObject multimediaObject = multimedia.getJSONObject(multimediaIndex);
                            //Checks to make sure multimedia item is an image
                            if(multimediaObject.optString(NEWS_STORY_MULTIMEDIA_TYPE_TAG).equals(NY_IMAGE_TYPE)) {
                                String multimediaFormat = multimediaObject.optString(NEWS_STORY_MULTIMEDIA_FORMAT_TAG);
                                String imageUrl = multimediaObject.optString(NEWS_STORY_MULTIMEDIA_URL_TAG);
                                //Checks for image type to know where to categorize image
                                if(multimediaFormat.equals(NY_FORMAT_THUMBNAIL_TYPE)) {
                                    newsStory.setThumbnail(imageUrl);
                                } else if(multimediaFormat.equals(NY_FORMAT_IMAGE_TYPE)) {
                                    newsStory.setImage(imageUrl);
                                }

                                //Fills image sections in case they're empty
                                if(TextUtils.isEmpty(newsStory.getThumbnail())) {
                                    newsStory.setThumbnail(imageUrl);
                                }
                                if(TextUtils.isEmpty(newsStory.getImage())) {
                                    newsStory.setImage(imageUrl);
                                }
                            }

                        }
                    }

                    //Puts full url in if short url wasn't found
                    if(TextUtils.isEmpty(newsStory.getShortUrl())) {
                        newsStory.setShortUrl(article.optString(NEWS_STORY_REGULARURL_TAG));
                    }
                    newsStories.add(newsStory);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsStories;
    }
}
