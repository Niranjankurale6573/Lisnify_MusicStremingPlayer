package com.musicstremingapplication.Models;

public class SongModel {
    int images;
    String song,artist;

    public SongModel(int images,String song,String artist)
    {
        this.images=images;
        this.song=song;
        this.artist=artist;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
