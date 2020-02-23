package com.example.liuxuetong;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.example.liuxuetong.login.userid;
import static com.example.liuxuetong.login.username;
import static com.example.liuxuetong.login.weburl;

public class tiezixqy extends AppCompatActivity {
    private TextView title;
    private TextView zuozhe;
    private TextView content;
    private ImageView img;
    private TextView school;
    private TextView time;
    private ListView replylist;
    private EditText replyedit;
    private String tzhftzid;
    private String zuozheid;
    final List<HashMap<String,String>> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tiezixqy);
        Intent intent=getIntent();
        String tzlbid=intent.getStringExtra("tzlbid");
        tzhftzid=tzlbid;
        String tzlbtitle=intent.getStringExtra("tzlbtitle");
        String tzlbuser=intent.getStringExtra("tzlbuserid");
        zuozheid=tzlbuser;
        String tzlbcontent=intent.getStringExtra("tzlbcontent");
        String tzlbschool=intent.getStringExtra("tzlbschool");
        String tzlbtime=intent.getStringExtra("tzlbtime");
        title = (TextView) findViewById(R.id.xqytitle);
        zuozhe = (TextView) findViewById(R.id.xqyzuozhe);
        content = (TextView) findViewById(R.id.xqycontent);
        img = (ImageView) findViewById(R.id.xqyimg);
        school = (TextView) findViewById(R.id.xqyschool);
        time = (TextView) findViewById(R.id.xqytime);
        title.setText(tzlbtitle);
        zuozhe.setText(tzlbuser);
        content.setText(tzlbcontent);
        school.setText(tzlbschool);
        time.setText(tzlbtime);

        replylist = (ListView)findViewById(R.id.pinlun);
//        final List<HashMap<String,String>> data = new ArrayList<>();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(weburl + "servlet/adGetReplyServlet?tid="+tzlbid+"").build();
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
                        map.put("retzid",jsonObject.getString("tid"));
                        map.put("ruser",jsonObject.getString("ruser"));
                        map.put("reuid",jsonObject.getString("id"));
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
        SimpleAdapter tzreplyAdapter= new SimpleAdapter(this,
                data,
                R.layout.tiezireplyitem,
                new String[]{"ruser","rcontent","rtime"},
                new int[]{R.id.reply_user,R.id.reply_content,R.id.reply_time});
        replylist.setAdapter(tzreplyAdapter);

    }
    public void replysubmit(View view){
        replyedit=(EditText)findViewById(R.id.pinglun);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        final String time=simpleDateFormat.format(date);
        System.out.println(tzhftzid);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(weburl + "servlet/adAddReplyServlet?rtieziid="+tzhftzid+"&tzuserid="+zuozheid+"&ruserid="+userid+"&rusername="+username+"&rcontent="+replyedit.getText().toString()+"&rtime="+time+"").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                HashMap<String,String> map = new HashMap<>();
                map.put("retzid",tzhftzid);
                map.put("ruser",username);
                map.put("reuid",userid);
                map.put("rcontent",replyedit.getText().toString());
                map.put("rtime",time);
                data.add(map);
                replyedit.setText("");
                Looper.prepare();
                Toast.makeText(tiezixqy.this,"评论成功",Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        });
        SimpleAdapter tzreplyAdapter= new SimpleAdapter(this,
                data,
                R.layout.tiezireplyitem,
                new String[]{"ruser","rcontent","rtime"},
                new int[]{R.id.reply_user,R.id.reply_content,R.id.reply_time});
        replylist.setAdapter(tzreplyAdapter);
    }
    public void tzxqyfh(View view){
        finish();
    }
}
