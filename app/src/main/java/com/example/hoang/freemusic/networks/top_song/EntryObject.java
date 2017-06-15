package com.example.hoang.freemusic.networks.top_song;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Hoang on 5/30/2017.
 */

public class EntryObject {
    @SerializedName("im:name")
    private NameObject nameObject;
    @SerializedName("im:image")
    private List<ImageObject> imageObjectList;
    @SerializedName("im:artist")
    private ArtistObject artistObject;

    public NameObject getNameObject() {
        return nameObject;
    }

    public void setNameObject(NameObject nameObject) {
        this.nameObject = nameObject;
    }

    public List<ImageObject> getImageObjectList() {
        return imageObjectList;
    }

    public void setImageObjectList(List<ImageObject> imageObjectList) {
        this.imageObjectList = imageObjectList;
    }

    public ArtistObject getArtistObject() {
        return artistObject;
    }

    public void setArtistObject(ArtistObject artistObject) {
        this.artistObject = artistObject;
    }
}
