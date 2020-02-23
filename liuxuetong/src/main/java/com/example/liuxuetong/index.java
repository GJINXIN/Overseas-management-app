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

public class index extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setContentView(R.layout.index);
            WebView webView=new WebView(this);
            setContentView(webView);
            WebSettings webSettings=webView.getSettings();
            webSettings.setAppCacheEnabled(true);
            webSettings.setJavaScriptEnabled(true);


        /*AssetManager assetManager=this.getAssets();
        try {
            InputStream inputStream=assetManager.open("shouye/index.html");
            byte[] buffer=new byte[inputStream.available()];
            inputStream.read(buffer);
            String html_content=new String(buffer,"utf-8");
            inputStream.close();
            webView.loadData(html_content,"text/html","utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        webView.addJavascriptInterface(this,"index");
        webView.loadUrl("file:///android_asset/shouye/index.html");
    }
    @JavascriptInterface
    public void openpaccount()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(index.this,paccount.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @JavascriptInterface
    public void opentzqz()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(index.this,teiziliebiao.class);
                String type="攻略求助";
                intent.putExtra("tztype",type);
                startActivity(intent);
            }
        });
    }
    @JavascriptInterface
    public void opentzdp()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(index.this,teiziliebiao.class);
                String type="点评";
                intent.putExtra("tztype",type);
                startActivity(intent);
            }
        });
    }
    @JavascriptInterface
    public void openschool()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(index.this,school.class);
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
                intent.setClass(index.this,neartz.class);
                startActivity(intent);
            }
        });
    }
    @JavascriptInterface
    public void openhouse()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(index.this,teiziliebiao.class);
                String type="房屋租赁";
                intent.putExtra("tztype",type);
                startActivity(intent);
            }
        });
    }
    @JavascriptInterface
    public void opencar()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(index.this,teiziliebiao.class);
                String type="车辆租赁";
                intent.putExtra("tztype",type);
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
                intent.setClass(index.this,nsearch.class);
                startActivity(intent);
            }
        });
    }
    @JavascriptInterface
    public void opentieziedit()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent();
                intent.setClass(index.this,tieziedit.class);
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
                intent.setClass(index.this,xiaoxiye.class);
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
