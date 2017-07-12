package com.example.adrian.mapa;

/**
 * Created by adrian on 15/06/2017.
 */

public class Song {

    private long id;
    private String title;
    private String artist;



    public Song(long songID, String songTitle, String songArtist) {
        id=songID;
        title=songTitle;

        artist=songArtist;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }
}
