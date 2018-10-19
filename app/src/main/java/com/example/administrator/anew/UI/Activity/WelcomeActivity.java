package com.example.administrator.anew.UI.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.anew.MainActivity;
import com.example.administrator.anew.R;
import com.example.administrator.anew.UI.wight.CustomVideoView;
import com.example.administrator.anew.Utils.ACache;
import com.example.administrator.anew.Utils.Constant;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{
    private CustomVideoView videoview;
    private ImageView images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
    }



    /**
     * 初始化
     */
    private void initView() {
        images= (ImageView) findViewById(R.id.images);
        images.setOnClickListener(this);

        videoview = (CustomVideoView) findViewById(R.id.videoview);
        videoview.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.sport));

        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
            }
        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.images:
                ACache.get(this).put(Constant.IS_SHOW_GUIDE,"0");
                Intent intent=new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }


    //返回重启加载
    @Override
    protected void onRestart() {
        super.onRestart();
        initView();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        super.onStop();
        videoview.stopPlayback();
    }

}
