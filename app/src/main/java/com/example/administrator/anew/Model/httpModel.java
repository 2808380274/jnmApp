package com.example.administrator.anew.Model;

import com.example.administrator.anew.DataBean.EntryBean;
import com.example.administrator.anew.Bean.ImgBean;
import com.example.administrator.anew.Bean.NewsBean;
import com.example.administrator.anew.HttpData.ApiService;
import com.example.administrator.anew.HttpData.HttpManger;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/9/22.
 */
//model用于提供数据模型
public class httpModel {
    HttpManger manger=new HttpManger();
    public Observable<NewsBean<com.example.administrator.anew.Bean.ResultBean>> getNews(String type, String key){
        ApiService apiService=manger.getRetrofit(manger.getOkhttp()).create(ApiService.class);
        return apiService.getApp(type,key);//实际上这个是Observable<NewBean<EntryBean>>
    }
   public Observable<ImgBean<EntryBean>> getImg(int count, int page){
       ApiService apiService =manger.Retrofit(manger.getOkhttp()).create(ApiService.class);
       return  apiService.getImg(count,page);
   }
}
