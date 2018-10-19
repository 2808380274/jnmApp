package com.example.administrator.anew.UI.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.anew.DataBean.LoginBean;
import com.example.administrator.anew.MainActivity;
import com.example.administrator.anew.MyView;
import com.example.administrator.anew.Presenter.LoginPresenter;
import com.example.administrator.anew.R;
import com.example.administrator.anew.UI.wight.FontTextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,MyView.LoginView{
    private FontTextView tv_register;
    private EditText et_phone;
    private EditText et_pwd;
    private Button btn_enter;
    LoginPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv_register= (FontTextView) findViewById(R.id.tv_register);
        et_phone= (EditText) findViewById(R.id.et_phone);
        et_pwd= (EditText) findViewById(R.id.et_pwd);
        btn_enter= (Button) findViewById(R.id.btn_enter);
        tv_register.setOnClickListener(this);
        btn_enter.setOnClickListener(this);
        presenter=new LoginPresenter(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_register:
                startActivity(new Intent(getApplicationContext(),UserActivity.class));
                break;
            case R.id.btn_enter:
                String phone=et_phone.getText().toString();
                String pwd=et_pwd.getText().toString();
                presenter.LoginData("00d91e8e0cca2b76f515926a36db68f5",phone,pwd);
                break;
        }
    }

    @Override
    public void success(LoginBean loginBean) {
        Toast.makeText(this, "登录结果：  " + loginBean.getMsg(), Toast.LENGTH_SHORT).show();
        if (loginBean.getMsg().equals("成功!")){
            Log.i("返回:",loginBean.getMsg().toString());
            try {
                Thread.sleep(1000);
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("name",loginBean.getData().getName().toString());
                intent.putExtra("text",loginBean.getData().getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void failed(int code) {

    }
}
