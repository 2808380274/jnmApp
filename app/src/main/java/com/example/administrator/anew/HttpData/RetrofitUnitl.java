package com.example.administrator.anew.HttpData;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/10/17.
 */

public class RetrofitUnitl {
    private Retrofit mRetrofit;
    private String baseUrl;
    OkHttpClient client;
    private static RetrofitUnitl mRetrofitManager;
    private RetrofitUnitl(String baseUrl, OkHttpClient client){
        this.baseUrl=baseUrl;
        this.client=client;
        initRetrofit();
    }

    //单例模式+双重锁模式 封装方法
    public static synchronized RetrofitUnitl getInstance(String baseUrl, OkHttpClient client){
        if (mRetrofitManager == null){
            mRetrofitManager = new RetrofitUnitl(baseUrl,client);
        }
        return mRetrofitManager;
    }

    //实例化Retrofit请求
    private void initRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    //封装泛型方法
    public <T> T setCreate(Class<T> reqServer) {
        return mRetrofit.create(reqServer);
    }

}
