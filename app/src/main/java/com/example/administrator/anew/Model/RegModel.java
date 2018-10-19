package com.example.administrator.anew.Model;

import com.example.administrator.anew.DataBean.UserBean;
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
 * Created by Administrator on 2018/10/17.
 */

public class RegModel {
    //https://www.apiopen.top/createUser?key=00d91e8e0cca2b76f515926a36db68f5&phone=19981016900&passwd=999999&name=%E5%B0%8F%E9%BB%91&text=%E5%93%88%E5%93%88
    public void getRegData(final String key,final String phone,final String name, String pwd,final String text, final ModelCallBack.RegCallBack callBack){
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
        map.put("name",name);
        map.put("passwd",pwd);
        map.put("text",text);
        RetrofitUnitl.getInstance("https://www.apiopen.top/",ok)
                .setCreate(ApiService.class)
                .getuser(key,map)
                .subscribeOn(Schedulers.io())               //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())  //最后在主线程中执行

                //进行事件的订阅，使用Consumer实现
                .subscribe(new Consumer<UserBean>() {
                    @Override
                    public void accept(UserBean userBean) throws Exception {
                        callBack.success(userBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBack.failed(throwable);
                    }
                });
    }

}
