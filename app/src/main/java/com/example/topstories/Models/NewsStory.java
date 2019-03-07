package com.example.topstories.Models;

public class NewsStory {
    private String title;
    private String shortUrl;
    private String thumbnail;
    private String image;
    private String description;

    public NewsStory(String title, String shortUrl, String description) {
        setTitle(title);
        setShortUrl(shortUrl);
        setDescription(description);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
