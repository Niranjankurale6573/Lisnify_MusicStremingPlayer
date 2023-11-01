package com.musicstremingapplication;

import java.io.Serializable;

public class Songs implements Serializable {

    private String songName,songUrl,SongArtist,SongtypeStr,Songlang;

    public Songs(String songName, String songUrl,String SongArtist,String SongtypeStr,String Songlang) {
        this.songName = songName;
        this.songUrl = songUrl;
        this.SongArtist = SongArtist;
        this.SongtypeStr=SongtypeStr;
        this.Songlang=Songlang;
    }

    public Songs() {
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getSongArtist() {
        return SongArtist;
    }

    public void setSongArtist(String songArtist) {
        SongArtist = songArtist;
    }

    public String getSongtypeStr() {
        return SongtypeStr;
    }

    public void setSongtypeStr(String songtypeStr) {
        SongtypeStr = songtypeStr;
    }

    public String getSonglang() {
        return Songlang;
    }

    public void setSonglang(String songlang) {
        Songlang = songlang;
    }
}

