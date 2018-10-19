package com.example.administrator.anew.Presenter;

import com.example.administrator.anew.DataBean.LoginBean;
import com.example.administrator.anew.Model.LoginModel;
import com.example.administrator.anew.Model.ModelCallBack;
import com.example.administrator.anew.MyView;

/**
 * Created by Administrator on 2018/10/18.
 */

public class LoginPresenter {
    LoginModel loginModel=new LoginModel();
    MyView.LoginView  loginView;

    public LoginPresenter(MyView.LoginView loginView) {
        this.loginView = loginView;
    }
    public void LoginData (String key,String phone,String pwd){
        loginModel.getLoginData(key, phone, pwd, new ModelCallBack.LoginCallBack() {
            @Override
            public void success(LoginBean loginBean) {
                loginView.success(loginBean);
            }

            @Override
            public void failed(Throwable code) {

            }
        });
    }
}
