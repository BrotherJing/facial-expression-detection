package com.brotherjing.facialexpdemo.netease;

/**
 * Created by Brotherjing on 2015-12-27.
 */
public class NetEaseSongBean {

    private String mp3Url;
    private String name;
    private int id;
    private int duration;

    public NetEaseSongBean() {
    }

    public void setMp3Url(String mp3Url) {
        this.mp3Url = mp3Url;
    }

    public String getMp3Url() {
        return this.mp3Url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return this.duration;
    }

}
