package com.brotherjing.facialexpdemo.netease;

import android.util.Log;

import com.brotherjing.facialexpdemo.GlobalEnv;
import com.brotherjing.facialexpdemo.bean.SearchResponse;
import com.brotherjing.facialexpdemo.bean.SongResponse;
import com.brotherjing.facialexpdemo.bean.douban.Music;
import com.brotherjing.facialexpdemo.bean.douban.TagSearchResponse;
import com.brotherjing.facialexpdemo.database.DBManager;
import com.brotherjing.facialexpdemo.retrofit.RetrofitClient;
import com.greendao.dao.Tag;

import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * Created by Brotherjing on 2015-12-27.
 */
public class NetEaseProvider {

    /*public static void nextSong(Response.Listener<NetEaseResponse> listener,Response.ErrorListener errorListener){
        long id = (long)(100000+Math.random()*1000000);
        String url = String.format("http://music.163.com/api/song/detail?id=%d&ids=[%d]", id, id);
        VolleyClient.getInstance().addRequest(new GsonRequest<NetEaseResponse>(Request.Method.GET,url,NetEaseResponse.class,listener,errorListener));
    }*/

    public static Observable<SongResponse> nextSong(){
        long id = (long)(100000+Math.random()*1000000);
        return RetrofitClient.getNetEaseApi().getSong(id + "", "[" + id + "]");
    }

    public static Observable<SearchResponse> nextSongWithTag(final String tag){
        Observable<TagSearchResponse> tagSearchResponseObservable;
        if(GlobalEnv.tagTotal.containsKey(tag)){
            int offset = GlobalEnv.tagTotal.get(tag);
            offset = offset>10?offset-10:0;
            tagSearchResponseObservable = RetrofitClient.getNetEaseApi().searchTagRange(tag, (int) (Math.random() *offset),10);
            Log.i("yj","search "+tag+" with offset "+offset);
        }else{
            tagSearchResponseObservable = RetrofitClient.getNetEaseApi().searchTag(tag);
        }
        return tagSearchResponseObservable.flatMap(new Func1<TagSearchResponse, Observable<Music>>() {
            @Override
            public Observable<Music> call(TagSearchResponse tagSearchResponse) {
                if (tagSearchResponse.getMusics().isEmpty()) return null;
                if (!GlobalEnv.tagTotal.containsKey(tag))
                    GlobalEnv.tagTotal.put(tag, tagSearchResponse.getTotal());
                return Observable.just(tagSearchResponse.getMusics().get((int) (Math.random() * tagSearchResponse.getCount())));
            }
        }).flatMap(new Func1<Music, Observable<SearchResponse>>() {
            @Override
            public Observable<SearchResponse> call(Music music) {
                GlobalEnv.currentMusic = music;
                Log.i("yj","search with title "+music.getTitle());
                return RetrofitClient.getNetEaseApi().search(music.getTitle(), 10, 0, 1);
            }
        });
    }

    public static Observable<SearchResponse> nextSongRandom(){
        Observable<Tag> tag = DBManager.getTag();
        return tag.flatMap(new Func1<Tag, Observable<SearchResponse>>() {
            @Override
            public Observable<SearchResponse> call(Tag tag) {
                Log.i("yj","start searching tag : "+tag.getName());
                return nextSongWithTag(tag.getName());
            }
        });
    }

    public static void updateTagOfLastSong(double hp){
        Music music = GlobalEnv.currentMusic;
        if(music==null)return;
        if(hp<=200)
            for(com.brotherjing.facialexpdemo.bean.douban.Tag tag:music.getTags()){
                if(tag.getName().length()>5)continue;
                DBManager.decreaseTagCount(tag.getName(),1);
            }
        else if(hp<=1000) {
            //do nothing
            /*for(com.brotherjing.facialexpdemo.bean.douban.Tag tag:music.getTags()){
                if(tag.getName().length()>5)continue;
                DBManager.decreaseTagCount(tag.getName(), 1);
            }*/
        }
        else
            for(com.brotherjing.facialexpdemo.bean.douban.Tag tag:music.getTags()){
                if(tag.getName().length()>5)continue;
                DBManager.increaseTagCount(tag.getName(),1);
            }
    }
}
