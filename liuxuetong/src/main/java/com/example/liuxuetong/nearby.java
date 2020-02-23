package com.example.liuxuetong;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class nearby extends AppCompatActivity {
    @SuppressLint("JavascriptInterface")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby);
        WebView webView = (WebView) findViewById(R.id.nearwv);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://m.dianping.com/");
//        webView.setWebViewClient(new WebViewClient());
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0,0);
        super.onPause();
    }
}
