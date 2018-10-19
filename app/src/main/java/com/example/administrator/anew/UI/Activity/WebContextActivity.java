package com.example.administrator.anew.UI.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.administrator.anew.R;

public class WebContextActivity extends FragmentActivity {
    private ProgressBar progressBar;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_context);
        initView();
        SettingsP();
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.wed);
        progressBar = (ProgressBar) findViewById(R.id.prog);
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        webView.loadUrl(url);
    }
    private void SettingsP() {
        WebSettings seting = webView.getSettings();
        seting.setJavaScriptEnabled(true);//设置webview支持javascript脚本

        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return false;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    // 加载中
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                }

            }
        });
    }


}
