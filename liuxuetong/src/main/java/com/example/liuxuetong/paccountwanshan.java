package com.example.liuxuetong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.liuxuetong.login.userage;
import static com.example.liuxuetong.login.useremail;
import static com.example.liuxuetong.login.userid;
import static com.example.liuxuetong.login.username;
import static com.example.liuxuetong.login.userpassword;
import static com.example.liuxuetong.login.userschool;
import static com.example.liuxuetong.login.usersex;
import static com.example.liuxuetong.login.weburl;

public class paccountwanshan extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText wname;
    private EditText wphone;
    private EditText wpassword;
    private EditText wsex;
    private EditText wage;
    private EditText wemail;
    private EditText wschool;
    private Button wsubmit;
    private Spinner spinner;
    private List<String> data;
    private ArrayAdapter adapter;
    private String schoolname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paccountwanshan);
        wname = (EditText) findViewById(R.id.name);
        wphone = (EditText) findViewById(R.id.phone);
        wpassword = (EditText) findViewById(R.id.password);
        wsex = (EditText) findViewById(R.id.sex);
        wage = (EditText) findViewById(R.id.age);
        wemail = (EditText) findViewById(R.id.email);
        wschool = (EditText) findViewById(R.id.school);

        wname.setText(username);
        wphone.setText(userid);
        wpassword.setText(userpassword);
        wsex.setText(usersex);
        wage.setText(userage);
        wemail.setText(useremail);
        wschool.setText(userschool);
        spinner = (Spinner)findViewById(R.id.spinner3);
        data=new ArrayList<String>();
        data.add(userschool);
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
    public void wanshan(final View view) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(weburl + "servlet/adUpdateUserServlet?sname="+wname.getText().toString()+"&sid="+wphone.getText().toString()+"&spassword="+wpassword.getText().toString()+"&ssex="+wsex.getText().toString()+"&sage="+wage.getText().toString()+"&semail="+wemail.getText().toString()+"&sschool="+wschool.getText().toString()+"").build();
        username = wname.getText().toString();
        usersex = wsex.getText().toString();
        useremail = wemail.getText().toString();
        userschool = wschool.getText().toString();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                view.postInvalidate();
            }
        });
    }
    public void fanhui(View view){
//        Intent intent = new Intent(paccountwanshan.this, paccount.class);
//        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        schoolname=data.get(position);
        wschool.setText(schoolname);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
