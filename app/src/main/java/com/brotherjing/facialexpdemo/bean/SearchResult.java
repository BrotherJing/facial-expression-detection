package com.brotherjing.facialexpdemo.bean;

import java.util.List;

/**
 * Created by Brotherjing on 2015-12-28.
 */
public class SearchResult {
    private int songCount;

    private List<SearchSong> songs ;

    public void setSongCount(int songCount){
        this.songCount = songCount;
    }
    public int getSongCount(){
        return this.songCount;
    }
    public void setSongs(List<SearchSong> songs){
        this.songs = songs;
    }
    public List<SearchSong> getSongs(){
        return this.songs;
    }
}
