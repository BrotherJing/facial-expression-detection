package com.brotherjing.facialexpdemo.bean.douban;

import java.util.List;

/**
 * Created by Brotherjing on 2015-12-30.
 */
public class Music {
    private List<Author> author ;

    private String alt_title;

    private String image;

    private List<Tag> tags ;

    private String title;

    private String id;

    public void setAuthor(List<Author> author){
        this.author = author;
    }
    public List<Author> getAuthor(){
        return this.author;
    }
    public void setAlt_title(String alt_title){
        this.alt_title = alt_title;
    }
    public String getAlt_title(){
        return this.alt_title;
    }
    public void setImage(String image){
        this.image = image;
    }
    public String getImage(){
        return this.image;
    }
    public void setTags(List<Tag> tags){
        this.tags = tags;
    }
    public List<Tag> getTags(){
        return this.tags;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }

}
