package com.brotherjing.facialexpdemo.bean;

/**
 * Created by Brotherjing on 2015-12-28.
 */
public class Album {
    private String name;

    private int id;

    private String type;

    private int size;

    private int status;

    private String description;

    private String tags;

    private int copyrightId;

    private long picId;

    private String briefDesc;

    private String picUrl;

    private String commentThreadId;

    private String company;

    private String blurPicUrl;

    private int companyId;

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
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }
    public void setSize(int size){
        this.size = size;
    }
    public int getSize(){
        return this.size;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setTags(String tags){
        this.tags = tags;
    }
    public String getTags(){
        return this.tags;
    }
    public void setCopyrightId(int copyrightId){
        this.copyrightId = copyrightId;
    }
    public int getCopyrightId(){
        return this.copyrightId;
    }
    public void setPicId(long picId){
        this.picId = picId;
    }
    public long getPicId(){
        return this.picId;
    }
    public void setBriefDesc(String briefDesc){
        this.briefDesc = briefDesc;
    }
    public String getBriefDesc(){
        return this.briefDesc;
    }
    public void setPicUrl(String picUrl){
        this.picUrl = picUrl;
    }
    public String getPicUrl(){
        return this.picUrl;
    }
    public void setCommentThreadId(String commentThreadId){
        this.commentThreadId = commentThreadId;
    }
    public String getCommentThreadId(){
        return this.commentThreadId;
    }
    public void setCompany(String company){
        this.company = company;
    }
    public String getCompany(){
        return this.company;
    }
    public void setBlurPicUrl(String blurPicUrl){
        this.blurPicUrl = blurPicUrl;
    }
    public String getBlurPicUrl(){
        return this.blurPicUrl;
    }
    public void setCompanyId(int companyId){
        this.companyId = companyId;
    }
    public int getCompanyId(){
        return this.companyId;
    }

}