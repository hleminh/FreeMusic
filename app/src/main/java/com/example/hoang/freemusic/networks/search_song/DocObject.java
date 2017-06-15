package com.example.hoang.freemusic.networks.search_song;

/**
 * Created by Hoang on 5/30/2017.
 */

public class DocObject {
    private String title;
    private String artist;
    private SourceLink source;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public SourceLink getSource() {
        return source;
    }

    public void setSource(SourceLink source) {
        this.source = source;
    }
}
