package com.brotherjing.facialexpdemo.bean;

import java.util.List;

/**
 * Created by Brotherjing on 2015-12-28.
 */
public class Songs {
    private boolean starred;

    private int popularity;

    private int starredNum;

    private int playedNum;

    private int dayPlays;

    private int hearTime;

    private String mp3Url;

    private String name;

    private int id;

    private int position;

    private int duration;

    private int status;

    private List<Artists> artists ;

    private int copyrightId;

    private int score;

    private Album album;

    private String commentThreadId;

    public void setStarred(boolean starred){
        this.starred = starred;
    }
    public boolean getStarred(){
        return this.starred;
    }
    public void setPopularity(int popularity){
        this.popularity = popularity;
    }
    public int getPopularity(){
        return this.popularity;
    }
    public void setStarredNum(int starredNum){
        this.starredNum = starredNum;
    }
    public int getStarredNum(){
        return this.starredNum;
    }
    public void setPlayedNum(int playedNum){
        this.playedNum = playedNum;
    }
    public int getPlayedNum(){
        return this.playedNum;
    }
    public void setDayPlays(int dayPlays){
        this.dayPlays = dayPlays;
    }
    public int getDayPlays(){
        return this.dayPlays;
    }
    public void setHearTime(int hearTime){
        this.hearTime = hearTime;
    }
    public int getHearTime(){
        return this.hearTime;
    }
    public void setMp3Url(String mp3Url){
        this.mp3Url = mp3Url;
    }
    public String getMp3Url(){
        return this.mp3Url;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setPosition(int position){
        this.position = position;
    }
    public int getPosition(){
        return this.position;
    }
    public void setDuration(int duration){
        this.duration = duration;
    }
    public int getDuration(){
        return this.duration;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setArtists(List<Artists> artists){
        this.artists = artists;
    }
    public List<Artists> getArtists(){
        return this.artists;
    }
    public void setCopyrightId(int copyrightId){
        this.copyrightId = copyrightId;
    }
    public int getCopyrightId(){
        return this.copyrightId;
    }
    public void setScore(int score){
        this.score = score;
    }
    public int getScore(){
        return this.score;
    }
    public void setAlbum(Album album){
        this.album = album;
    }
    public Album getAlbum(){
        return this.album;
    }
    public void setCommentThreadId(String commentThreadId){
        this.commentThreadId = commentThreadId;
    }
    public String getCommentThreadId(){
        return this.commentThreadId;
    }

}