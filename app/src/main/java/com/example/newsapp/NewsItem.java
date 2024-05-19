package com.example.newsapp;

public class NewsItem {
    private String title;
    private String description;
    private String link;
    private String image_url;

    public NewsItem(String title, String description, String link, String image_url) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getLink() {
        return link;
    }
}
