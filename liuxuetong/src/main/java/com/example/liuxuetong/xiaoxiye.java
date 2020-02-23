package com.example.liuxuetong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.liuxuetong.login.userid;
import static com.example.liuxuetong.login.weburl;

public class xiaoxiye extends AppCompatActivity {
    private ListView xiaoxilv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiaoxiye);
        xiaoxilv = (ListView)findViewById(R.id.xiaoxilv);
        final List<HashMap<String,String>> data = new ArrayList<>();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(weburl + "servlet/adGetXiaoxiServlet?usid="+userid+"").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                String json = response.body().string();

                try{
                    JSONArray jsonArray=new JSONArray(json);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        HashMap<String,String> map = new HashMap<>();
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        System.out.println(i);
                        map.put("tid",jsonObject.getString("tid"));
                        map.put("uid",jsonObject.getString("uid"));
                        map.put("ruser",jsonObject.getString("ruser"));
                        map.put("id",jsonObject.getString("id"));
                        map.put("rcontent",jsonObject.getString("rcontent"));
                        map.put("rtime",jsonObject.getString("rtime"));
                        data.add(map);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(json);
            }
        });
        SimpleAdapter tzfllistAdapter= new SimpleAdapter(this,
                data,
                R.layout.xiaoxiitem,
                new String[]{"ruser","rcontent","rtime"},
                new int[]{R.id.tv_title,R.id.tv_date,R.id.tv_time});
        xiaoxilv.setAdapter(tzfllistAdapter);
        xiaoxilv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @SuppressWarnings("unchecked")
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ListView listView = (ListView)parent;
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String tzlbid = map.get("tid");
                String tzlbuserid = map.get("uid");
                String tzlbtitle = map.get("ruser");
                String tzlbcontent= map.get("id");
                String tzlbimg = map.get("rcontent");
                String tzlbtime = map.get("rtime");
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.get().url(weburl + "servlet/adSearchtzbyxiaoxiServlet?tid="+tzlbid+"").build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        try{
                            String json = response.body().string();
                            JSONObject jsonObject = new JSONObject(json);
                            String tzlbid=jsonObject.getString("tid");
                            String tzlbuserid=jsonObject.getString("id");
                            String tzbtitle=jsonObject.getString("title");
                            String tzlbcontent=jsonObject.getString("content");
                            String tzlbimg=jsonObject.getString("img");
                            String tzlbtime=jsonObject.getString("time");
                            String tzlbschool=jsonObject.getString("school");
                            Intent intent =new Intent(xiaoxiye.this,tiezixqy.class);
                            intent.putExtra("tzlbid",tzlbid);
                            intent.putExtra("tzlbuserid",tzlbuserid);
                            intent.putExtra("tzlbtitle",tzbtitle);
                            intent.putExtra("tzlbcontent",tzlbcontent);
                            intent.putExtra("tzlbimg",tzlbimg);
                            intent.putExtra("tzlbtime",tzlbtime);
                            intent.putExtra("tzlbschool",tzlbschool);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
    public void xiaoxifh(View view){
        finish();
    }
}
