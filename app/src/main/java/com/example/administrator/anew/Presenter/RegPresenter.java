package com.example.administrator.anew.Presenter;


import com.example.administrator.anew.DataBean.UserBean;
import com.example.administrator.anew.Model.ModelCallBack;
import com.example.administrator.anew.Model.RegModel;
import com.example.administrator.anew.MyView;

/**
 * Created by Administrator on 2018/10/17.
 */

public class RegPresenter {
    RegModel regModel = new RegModel();
    MyView.RegView regView;
    public RegPresenter(MyView.RegView regView) {
        this.regView = regView;
    }

    public void getData(String key,String phone, String name,String pwd,String text) {
        regModel.getRegData(key, phone, name, pwd, text, new ModelCallBack.RegCallBack() {
            @Override
            public void success(UserBean userBean) {
                regView.sucess(userBean);
            }

            @Override
            public void failed(Throwable code) {

            }
        });


    }
}
