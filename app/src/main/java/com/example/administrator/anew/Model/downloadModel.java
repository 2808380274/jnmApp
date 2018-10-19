package com.example.administrator.anew.Model;

import com.example.administrator.anew.DataBean.LoginBean;
import com.example.administrator.anew.HttpData.ApiService;
import com.example.administrator.anew.HttpData.RetrofitUnitl;
import com.example.administrator.anew.Utils.LoggingInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/10/19.
 */

public class downloadModel {
    public void getImgData(final String path_url, final  ModelCallBack.ImgCallBack callBack){
        //使用okhttp请求,添加拦截器时把下面代码解释
        OkHttpClient ok = new OkHttpClient.Builder()
                .connectTimeout(20000, TimeUnit.SECONDS)
                .writeTimeout(20000,TimeUnit.SECONDS)
                .readTimeout(20000,TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .build();

        RetrofitUnitl.getInstance(ApiService.Img_url,ok)
                .setCreate(ApiService.class)
                .downLoadImg(path_url)
                .subscribeOn(Schedulers.io())               //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())  //最后在主线程中执行
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        callBack.success(responseBody);
                    }
                });
        //进行事件的订阅，使用Consumer实现

    }

}
