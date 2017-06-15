package com.example.hoang.freemusic.databases.models;

/**
 * Created by Hoang on 5/30/2017.
 */

public class TopSongModel {
    private String songName;
    private String smallImage;
    private String largeImage;
    private String artistName;
    private String linkSource;

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getLinkSource() {
        return linkSource;
    }

    public void setLinkSource(String linkSource) {
        this.linkSource = linkSource;
    }

    @Override
    public String toString() {
        return "TopSongModel{" +
                "songName='" + songName + '\'' +
                ", smallImage='" + smallImage + '\'' +
                ", largeImage='" + largeImage + '\'' +
                ", artistName='" + artistName + '\'' +
                '}';
    }
}
