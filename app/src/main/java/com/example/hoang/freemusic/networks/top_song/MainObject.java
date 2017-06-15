package com.example.hoang.freemusic.networks.top_song;

/**
 * Created by Hoang on 5/30/2017.
 */

public class MainObject {
    private Feed feed;

    public MainObject(Feed feed) {
        this.feed = feed;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }
}
