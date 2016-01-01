package com.brotherjing.facialexpdemo.bean;

import java.util.List;

/**
 * Created by Brotherjing on 2015-12-28.
 */
public class SongResponse {
    private List<Songs> songs ;

    private int code;

    public void setSongs(List<Songs> songs){
        this.songs = songs;
    }
    public List<Songs> getSongs(){
        return this.songs;
    }
    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }

}