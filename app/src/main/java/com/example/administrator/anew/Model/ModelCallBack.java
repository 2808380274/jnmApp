package com.example.administrator.anew.Model;

import com.example.administrator.anew.DataBean.LoginBean;
import com.example.administrator.anew.DataBean.UserBean;

import okhttp3.ResponseBody;


/**
 * Created by Administrator on 2018/10/17.
 */

public interface ModelCallBack {
    public interface LoginCallBack{
        //登录时，数据获取成功的方法，返回一个值表示登陆成功
        public void success(LoginBean loginBean);
        //登录时，数据获取失败的方法，返回一个int值响应码表示登陆失败
        public void failed(Throwable code);
    }

    public interface RegCallBack {
        //注册时，数据获取成功的方法，返回一个值表示登陆成功
        public void success(UserBean userBean);
        //注册时，数据获取失败的方法，返回一个int值响应码表示登陆失败
        public void failed(Throwable code);
    }
    public interface  ImgCallBack{
        //下载
        public void success(ResponseBody value);

    }
}
