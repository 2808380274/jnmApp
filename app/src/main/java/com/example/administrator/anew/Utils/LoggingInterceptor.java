package com.example.administrator.anew.Utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/10/17.
 */

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        //开始响应的时间
        long t1 = System.nanoTime();
        //执行请求，返回响应的数据
        Response response = chain.proceed(request);
        //请求结束，响应返回
        long t2 = System.nanoTime();

        //响应的耗费时间
        System.out.println(" this request  url  " + response.request().url() + "  "  + (t2 - t1)/1000000);

        return response;

    }
}
