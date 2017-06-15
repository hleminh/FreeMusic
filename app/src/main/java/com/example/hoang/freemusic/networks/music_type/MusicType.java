package com.example.hoang.freemusic.networks.music_type;

import java.util.List;

/**
 * Created by Hoang on 5/23/2017.
 */

public class MusicType {
    private String id;
    private String name;
    private List<MusicType> subgenres;

    public MusicType(String id, String name, List<MusicType> subgenres) {
        this.id = id;
        this.name = name;
        this.subgenres = subgenres;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "MusicType{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
