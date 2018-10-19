package com.example.administrator.anew.UI.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.anew.DataBean.UserBean;
import com.example.administrator.anew.MyView;
import com.example.administrator.anew.Presenter.RegPresenter;
import com.example.administrator.anew.R;

public class UserActivity extends AppCompatActivity implements View.OnClickListener, MyView.RegView {

    private EditText tv_signature;  //签名
    private EditText tv_pwd;         //密码
    private EditText tv_phone;       //手机号
    private EditText tv_user;        //昵称
    private Button  btn_enter;       //注册
    private RegPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
        presenter = new RegPresenter(this);
    }

    private void initView() {
        tv_user= (EditText) findViewById(R.id.tv_user);
        tv_phone= (EditText) findViewById(R.id.tv_phone);
        tv_pwd= (EditText) findViewById(R.id.tv_pwd);
        tv_signature= (EditText) findViewById(R.id.tv_signature);
        btn_enter= (Button) findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
      String  phone=tv_phone.getText().toString();
       String pwd=tv_pwd.getText().toString();
        String name=tv_user.getText().toString();
        String text=tv_signature.getText().toString();
    /*    if (!Utils.checkUsername(phone)&&!Utils.checkPassword(pwd)){
            Toast.makeText(this,"请检查手机号或密码",Toast.LENGTH_LONG).show();
        }*/
       presenter.getData("00d91e8e0cca2b76f515926a36db68f5",phone,name,pwd,text);
    }

    @Override
    public void failed(int code) {

    }

    @Override
    public void sucess(UserBean userBean) {
            Toast.makeText(this,userBean.getMsg(),Toast.LENGTH_SHORT).show();
            if (userBean.getMsg().equals("成功!")){

                try {
                    Thread.sleep(2000);
                    Toast.makeText(this, "即将跳转到登录页面", Toast.LENGTH_SHORT).show();
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
    }
}
