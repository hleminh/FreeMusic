package com.example.hoang.freemusic.networks.top_song;

import java.util.List;

/**
 * Created by Hoang on 5/30/2017.
 */

public class Feed {
    private List<EntryObject> entry;

    public List<EntryObject> getEntry() {
        return entry;
    }

    public void setEntry(List<EntryObject> entry) {
        this.entry = entry;
    }
}
