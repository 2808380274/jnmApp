package com.example.administrator.anew.HttpData;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/9/22.
 */

public class HttpManger {
    public OkHttpClient  getOkhttp(){
        // log用拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        // 开发模式记录整个body，否则只记录基本信息如返回200，http协议版本等
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 如果使用到HTTPS，我们需要创建SSLSocketFactory，并设置到client
//        SSLSocketFactory sslSocketFactory = null;

        return new OkHttpClient.Builder()
                // HeadInterceptor实现了Interceptor，用来往Request Header添加一些业务相关数据，如APP版本，token信息
//                .addInterceptor(new HeadInterceptor())
                .addInterceptor(logging)
                // 连接超时时间设置
                .connectTimeout(10, TimeUnit.SECONDS)
                // 读取超时时间设置
                .readTimeout(10, TimeUnit.SECONDS)

                .build();
    }
    public Retrofit getRetrofit(OkHttpClient okHttpClient){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ApiService.Base_url)      //url地址
                .addConverterFactory(GsonConverterFactory.create())   //添加解析适配器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //添加网络适配器
                .client(okHttpClient)
                .build();
        return  retrofit;

}
    public Retrofit Retrofit(OkHttpClient okHttpClient){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ApiService.Data_url)      //url地址
                .addConverterFactory(GsonConverterFactory.create())   //添加解析适配器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //添加网络适配器
                .client(okHttpClient)
                .build();
        return  retrofit;

    }
    public Retrofit ImgRetrofit(OkHttpClient okHttpClient){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ApiService.Img_url)      //url地址
                .addConverterFactory(GsonConverterFactory.create())   //添加解析适配器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //添加网络适配器
                .client(okHttpClient)
                .build();
        return  retrofit;

    }
}
