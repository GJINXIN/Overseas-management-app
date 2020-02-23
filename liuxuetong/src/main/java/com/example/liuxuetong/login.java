package com.example.liuxuetong;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class login extends AppCompatActivity {
    private EditText user;
    private EditText psw;
    private Button bt;
    private TextView zhuce;
    private String jso;

    public static String userid;
    public static String userpassword;
    public static String username;
    public static String usersex;
    public static String userage;
    public static String useremail;
    public static String userschool;

    public static String weburl="http://192.168.1.6:8080/SalarySystem/";
    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);
    user = (EditText) findViewById(R.id.name);
    psw = (EditText) findViewById(R.id.password);
    zhuce = (TextView) findViewById(R.id.zc);
}
public void doGet(View view) throws IOException {
    OkHttpClient okHttpClient = new OkHttpClient();
    Request.Builder builder = new Request.Builder();
    Request request = builder.get().url(weburl + "servlet/adLoginServlet?name="+user.getText().toString()+"&password="+psw.getText().toString()+"&type=user").build();

    Call call = okHttpClient.newCall(request);
    call.enqueue(new Callback() {
        @Override
        public void onFailure(Request request, IOException e) {

        }

        @Override
        public void onResponse(Response response) throws IOException {
            try {
                String flag;
                String json = response.body().string();
                JSONObject jsonObject = new JSONObject(json);
                username = jsonObject.getString("name");
                userpassword =jsonObject.getString("password");
                userid =jsonObject.getString("id");
                usersex =jsonObject.getString("sex");
                userage =jsonObject.getString("age");
                userschool =jsonObject.getString("school");
                useremail =jsonObject.getString("email");
                flag = jsonObject.getString("flag");
                Log.e("json", "userlist = " + json);
                if (flag.equals("1")) {
                    Intent intent = new Intent(login.this, index.class);
                    startActivity(intent);
                    finish();
                    Looper.prepare();
                    Toast.makeText(login.this,"登录成功",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
                else if(flag.equals("2")){
                    Looper.prepare();
                    Toast.makeText(login.this,"密码错误",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
                else {
                    Looper.prepare();
                    Toast.makeText(login.this,"不存在此账号",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    });
}
public void zhce(View view){
    Intent intent=new Intent(login.this, register.class); startActivity(intent);

}
}
