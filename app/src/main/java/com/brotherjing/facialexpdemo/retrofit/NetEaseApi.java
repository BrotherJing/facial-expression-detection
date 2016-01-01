package com.brotherjing.facialexpdemo.retrofit;


import com.brotherjing.facialexpdemo.bean.SearchResponse;
import com.brotherjing.facialexpdemo.bean.SongResponse;
import com.brotherjing.facialexpdemo.bean.douban.TagSearchResponse;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Brotherjing on 2015-12-28.
 */
public interface NetEaseApi {

    //?id={id}&ids=[{id}]
    @GET("http://music.163.com/api/song/detail")
    Observable<SongResponse> getSong(@Query("id") String id,@Query("ids") String ids);

    //&s={keyword}&limit={limit}&offset={offset}
    @GET("http://s.music.163.com/search/get")
    Observable<SearchResponse> search(@Query("s") String keyword, @Query("limit") int limit, @Query("offset") int offset, @Query("type") int type);

    @GET("https://api.douban.com/v2/music/search")
    Observable<TagSearchResponse> searchTagRange(@Query("tag")String tag,@Query("start")int start,@Query("count")int count);

    @GET("https://api.douban.com/v2/music/search")
    Observable<TagSearchResponse> searchTag(@Query("tag")String tag);
}
