package com.example.administrator.anew.UI.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.anew.MainActivity;
import com.example.administrator.anew.R;
import com.example.administrator.anew.Utils.ACache;
import com.example.administrator.anew.Utils.Constant;

public class SplahActivity extends AppCompatActivity {


    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splah);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Welcome();
            }
        },3000);
    }


    private void Welcome() {
        String isShowGuide =  ACache.get(this).getAsString(Constant.IS_SHOW_GUIDE);

        // 第一次启动进入引导页面
        if(null == isShowGuide){
                    startActivity(new Intent(getApplicationContext(),WelcomeActivity.class));
        }
        else{
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }


        this.finish();
    }

}
