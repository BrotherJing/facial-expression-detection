package com.brotherjing.facialexpdemo.bean.douban;

import java.util.List;

/**
 * Created by Brotherjing on 2015-12-30.
 */
public class TagSearchResponse {
    private int count;

    private int start;

    private int total;

    private List<Music> musics ;

    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    public void setStart(int start){
        this.start = start;
    }
    public int getStart(){
        return this.start;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }
    public void setMusics(List<Music> musics){
        this.musics = musics;
    }
    public List<Music> getMusics(){
        return this.musics;
    }

}

