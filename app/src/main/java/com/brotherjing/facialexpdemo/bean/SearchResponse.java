package com.brotherjing.facialexpdemo.bean;

/**
 * Created by Brotherjing on 2015-12-28.
 */
public class SearchResponse {
    private SearchResult result;

    private int code;

    public void setResult(SearchResult result){
        this.result = result;
    }
    public SearchResult getResult(){
        return this.result;
    }
    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
}
