package com.brotherjing.facialexpdemo.netease;

import java.util.List;

/**
 * Created by Brotherjing on 2015-12-27.
 */
public class NetEaseResponse {

    private List<NetEaseSongBean> songs ;

    private int code;

    public void setSongs(List<NetEaseSongBean> songs){
        this.songs = songs;
    }
    public List<NetEaseSongBean> getSongs(){
        return this.songs;
    }
    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }

}
