package com.brotherjing.facialexpdemo.bean;

import java.util.List;

/**
 * Created by Brotherjing on 2015-12-30.
 */
public class SearchSong {
    private int id;

    private String name;

    private List<Artists> artists ;

    private Album album;

    private String audio;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setArtists(List<Artists> artists){
        this.artists = artists;
    }
    public List<Artists> getArtists(){
        return this.artists;
    }
    public void setAlbum(Album album){
        this.album = album;
    }
    public Album getAlbum(){
        return this.album;
    }
    public void setAudio(String audio){
        this.audio = audio;
    }
    public String getAudio(){
        return this.audio;
    }

}
