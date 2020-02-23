package com.example.liuxuetong;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class paccount extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.paccount);
        WebView webView=new WebView(this);
        setContentView(webView);
        WebSettings webSettings=webView.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this,"paccount");
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl("file:///android_asset/paccount/index.html");
        String js;
        js="javascript:document.getElementById('username').value='gjx'";
        webView.evaluateJavascript(js,null);
    }
//    @JavascriptInterface
//    public String getUserLogin(){
//        SharedPreferences sharedPreferences = getSharedPreferences("user_login_name",MODE_PRIVATE);
//        return sharedPreferences.getString("user_login_name","用户");
//    }


    @JavascriptInterface
    public void openpersontz()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(paccount.this,owntiezi.class);
                startActivity(intent);
            }
        });
    }
    @JavascriptInterface
    public void openindex()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(paccount.this,index.class);
                startActivity(intent);
            }
        });
    }
    @JavascriptInterface
    public void opentiezisc()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(paccount.this,tiezisc.class);
                startActivity(intent);
            }
        });
    }
    @JavascriptInterface
    public void openwpmm()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(paccount.this,wpmm.class);
                startActivity(intent);
            }
        });
    }
    @JavascriptInterface
    public void opennearby()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(paccount.this,neartz.class);
                startActivity(intent);
            }
        });
    }
    @JavascriptInterface
    public void opensearch()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(paccount.this,nsearch.class);
                startActivity(intent);
            }
        });
    }
    @JavascriptInterface
    public void openxiaoxi()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(paccount.this,xiaoxiye.class);
                startActivity(intent);
            }
        });
    }
    @JavascriptInterface
    public void openpaccountwanshan()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(paccount.this,paccountwanshan.class);
                startActivity(intent);
            }
        });
    }
    @JavascriptInterface
    public void openlogin()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(paccount.this,login.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onPause() {
        overridePendingTransition(0,0);
        super.onPause();
    }
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一下退出应用",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                Intent i = new Intent(Intent.ACTION_MAIN);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addCategory(Intent.CATEGORY_HOME);
                startActivity(i);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
