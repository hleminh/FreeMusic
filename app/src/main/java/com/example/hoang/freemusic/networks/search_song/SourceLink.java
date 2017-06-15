package com.example.hoang.freemusic.networks.search_song;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hoang on 5/30/2017.
 */

public class SourceLink {
    @SerializedName("128")
    private String linkSource;

    public String getLinkSource() {
        return linkSource;
    }

    public void setLinkSource(String linkSource) {
        this.linkSource = linkSource;
    }
}
