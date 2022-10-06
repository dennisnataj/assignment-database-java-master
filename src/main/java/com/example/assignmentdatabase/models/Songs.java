package com.example.assignmentdatabase.models;

public class Songs {
    private String songName;

    public Songs(String songName) {
        this.songName = songName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
