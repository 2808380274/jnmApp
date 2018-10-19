package com.example.administrator.anew.HttpData;

import com.example.administrator.anew.Bean.ImgBean;
import com.example.administrator.anew.Bean.NewsBean;
import com.example.administrator.anew.Bean.ResultBean;
import com.example.administrator.anew.DataBean.EntryBean;
import com.example.administrator.anew.DataBean.LoginBean;
import com.example.administrator.anew.DataBean.UserBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Created by Administrator on 2018/9/22.
 */

public interface ApiService {
    public static final String Img_url="https://ww1.sinaimg.cn/";
    public static final String Base_url="http://v.juhe.cn/toutiao/";
    public static final String Data_url="https://gank.io/api/data/%E7%A6%8F%E5%88%A9/";  //福利
    @GET("index")
    Observable<NewsBean<ResultBean>>getApp(@Query("type") String type, @Query("key") String key);

    @GET("{count}/{page}")
    Observable<ImgBean<EntryBean>>getImg(@Path("count") int count, @Path("page") int page);

     //注册
    //https://www.apiopen.top/createUser?key=00d91e8e0cca2b76f515926a36db68f5&phone=13594347817&passwd=123654
    @FormUrlEncoded
    @POST("createUser")
    Observable<UserBean>getuser(@Field("key") String key,@FieldMap Map<String ,String> map);  //从key ,phone passwd name  text

    //登录
    //https://www.apiopen.top/login?key=00d91e8e0cca2b76f515926a36db68f5&phone=13594347817&passwd=123456
    @FormUrlEncoded
    @POST("login")
    Observable<LoginBean>getlogin(@Field("key") String key,@FieldMap Map<String,String> map); //从key phone passwd

    //下载
    @GET("large/{fileName}")  //{fileName}是动态码
    @Streaming
    //GET下载文件必须结合@Streaming使用
    Observable<ResponseBody> downLoadImg(@Path("fileName") String fileName);


}
