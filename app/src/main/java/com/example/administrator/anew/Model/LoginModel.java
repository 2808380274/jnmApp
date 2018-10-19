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

/**
 * Created by Administrator on 2018/10/18.
 */

public class LoginModel {
    public void getLoginData(final String key,final String phone, String pwd, final  ModelCallBack.LoginCallBack callBack){
        //使用okhttp请求,添加拦截器时把下面代码解释
        OkHttpClient ok = new OkHttpClient.Builder()
                .connectTimeout(20000, TimeUnit.SECONDS)
                .writeTimeout(20000,TimeUnit.SECONDS)
                .readTimeout(20000,TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .build();

        //使用Retrofit结合RxJava，okhttp封装类的单例模式
        Map<String,String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("passwd",pwd);

        RetrofitUnitl.getInstance("https://www.apiopen.top/",ok)
                .setCreate(ApiService.class)
                .getlogin(key,map)
                .subscribeOn(Schedulers.io())               //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())  //最后在主线程中执行
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) throws Exception {
                        callBack.success(loginBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBack.failed(throwable);
                    }
                });
                //进行事件的订阅，使用Consumer实现

    }

}
