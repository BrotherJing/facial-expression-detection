package com.brotherjing.facialexpdemo.retrofit;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by Brotherjing on 2015-12-28.
 */
public class RetrofitClient {

    public static final String BASE_URL = "http://music.163.com";

    private static OkHttpClient client;
    private static Retrofit retrofit;

    public static NetEaseApi netEaseApi;

    public static void init(){
        client = new OkHttpClient();
        client.interceptors().add(new HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT));
        //client.interceptors().add(interceptor);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        netEaseApi = retrofit.create(NetEaseApi.class);
    }

    public static NetEaseApi getNetEaseApi() {
        return netEaseApi;
    }


    private static Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newReq = chain.request().newBuilder().build();
            //add headers here
            return chain.proceed(newReq);
        }
    };
}
