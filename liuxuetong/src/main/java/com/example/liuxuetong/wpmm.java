package com.example.liuxuetong;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class wpmm extends AppCompatActivity {
    @SuppressLint("JavascriptInterface")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.wpmm);
        WebView webView=new WebView(this);
        setContentView(webView);
        WebSettings webSettings=webView.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this,"wpmm");
        webView.loadUrl("file:///android_asset/wpmm/index.html");
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0,0);
        super.onPause();
    }
}
