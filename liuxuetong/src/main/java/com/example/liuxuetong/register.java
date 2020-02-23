package com.example.liuxuetong;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.liuxuetong.login.weburl;

public class register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText zname;
    private EditText zphone;
    private EditText zpassword;
    private Button zbutton;
    private Spinner spinner;
    private List<String> data;
    private ArrayAdapter adapter;
    private String schoolname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        zname = (EditText) findViewById(R.id.zcname);
        zphone = (EditText) findViewById(R.id.zcphone);
        zpassword = (EditText) findViewById(R.id.zcpassword);
        spinner = (Spinner)findViewById(R.id.spinner2);
        data=new ArrayList<String>();
        data.add("哈佛大学");
        data.add("剑桥大学");
        data.add("纽约大学");
        data.add("哥伦比亚大学");
        //2、未下来列表定义一个数组适配器
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,data);
        //3、为适配器设置下拉菜单的样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //4、将适配器配置到下拉列表上
        spinner.setAdapter(adapter);
        //5、给下拉菜单设置监听事件
        spinner.setOnItemSelectedListener(this);
    }
    public void regis(View view) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(weburl + "servlet/adAddUserServlet?name="+zname.getText().toString()+"&phone="+zphone.getText().toString()+"&school="+schoolname+"&password="+zpassword.getText().toString()+"").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();

                Log.e("json", "onResponse = " + json);
                if (json.equals("1")) {
                    Intent intent = new Intent(register.this, login.class);
                    startActivity(intent);
                    Looper.prepare();
                    Toast.makeText(register.this,"注册成功",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        schoolname = data.get(position);
        System.out.println(schoolname);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
