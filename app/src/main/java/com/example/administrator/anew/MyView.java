package com.example.administrator.anew;


import com.example.administrator.anew.DataBean.LoginBean;
import com.example.administrator.anew.DataBean.UserBean;

import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/10/17.
 */

public class MyView {
    //登录页面由presenter层交互数据到view层MainActivity
    public interface LoginView{
        //登录时，数据获取成功的方法，返回一个值表示登陆成功
        void success(LoginBean loginBean);
        //登录时，数据获取失败的方法，返回一个int值响应码表示登陆失败
        void failed(int code);
    }

    //注册页面由presenter层到view层RegActivity
    public interface RegView{

        void failed(int code);
        void sucess(UserBean userBean);
    }
    public interface ImgView {
        void showding();
        void dismiss();
        void success(ResponseBody value);
    }
}
