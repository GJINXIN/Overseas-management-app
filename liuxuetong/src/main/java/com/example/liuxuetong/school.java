package com.example.liuxuetong;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class school extends AppCompatActivity {
    @SuppressLint("JavascriptInterface")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.school);
        WebView webView=new WebView(this);
        setContentView(webView);
        WebSettings webSettings=webView.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this,"school");
        webView.loadUrl("file:///android_asset/school/index.html");
    }
    @JavascriptInterface
    public void opennearby()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(school.this,neartz.class);
                startActivity(intent);
            }
        });
    }
    @JavascriptInterface
    public void scfh()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }
    @Override
    protected void onPause() {
        overridePendingTransition(0,0);
        super.onPause();
    }
}
