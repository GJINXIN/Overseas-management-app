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

import static com.example.liuxuetong.login.userschool;
import static com.example.liuxuetong.login.weburl;

public class neartz extends AppCompatActivity {
    private ListView neartiezi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.neartz);
        neartiezi = (ListView)findViewById(R.id.neartzlv);
        System.out.println(userschool);
        final List<HashMap<String,String>> data = new ArrayList<>();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(weburl + "servlet/adneartieziServlet?search="+userschool+"").build();
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
                        map.put("tzid",jsonObject.getString("tid"));
                        map.put("tzuserid",jsonObject.getString("id"));
                        map.put("tztitle",jsonObject.getString("title"));
                        map.put("tzcontent",jsonObject.getString("content"));
                        map.put("tzimg",jsonObject.getString("img"));
                        map.put("tztime",jsonObject.getString("time"));
                        map.put("tzschool",jsonObject.getString("school"));
                        data.add(map);
//                        String tieziid=jsonObject.getString("tid");
//                        String userid=jsonObject.getString("id");
//                        String tztitle=jsonObject.getString("tiele");
//                        String tzcontent=jsonObject.getString("content");
//                        String tzimg=jsonObject.getString("img");
//                        String tztime=jsonObject.getString("time");
//                        String tiezischool=jsonObject.getString("school");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(json);
            }
        });
        SimpleAdapter tzfllistAdapter= new SimpleAdapter(this,
                data,
                R.layout.tieziliebiao,
                new String[]{"tztitle","tzcontent","tzschool","tztime"},
                new int[]{R.id.tv_title,R.id.tv_date,R.id.tv_school,R.id.tv_time});
        neartiezi.setAdapter(tzfllistAdapter);
        neartiezi.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @SuppressWarnings("unchecked")
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ListView listView = (ListView)parent;
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String tzlbid = map.get("tzid");
                String tzlbuserid = map.get("tzuserid");
                String tzlbtitle = map.get("tztitle");
                String tzlbcontent= map.get("tzcontent");
                String tzlbimg = map.get("tzimg");
                String tzlbtime = map.get("tztime");
                String tzlbschool = map.get("tzschool");
                System.out.println(tzlbtime);
                Intent intent =new Intent(neartz.this,tiezixqy.class);
                intent.putExtra("tzlbid",tzlbid);
                intent.putExtra("tzlbuserid",tzlbuserid);
                intent.putExtra("tzlbtitle",tzlbtitle);
                intent.putExtra("tzlbcontent",tzlbcontent);
                intent.putExtra("tzlbimg",tzlbimg);
                intent.putExtra("tzlbtime",tzlbtime);
                intent.putExtra("tzlbschool",tzlbschool);
                startActivity(intent);
            }
        });
    }
    public  void neartzfh(View view){
        finish();
    }
    public void neartzweb(View view){
        Intent intent=new Intent(neartz.this,nearby.class);
        startActivity(intent);
    }
}
