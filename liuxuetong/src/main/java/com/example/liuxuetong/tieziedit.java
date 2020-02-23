package com.example.liuxuetong;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.liuxuetong.login.userid;
import static com.example.liuxuetong.login.userschool;
import static com.example.liuxuetong.login.weburl;


public class tieziedit extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private ImageView iv_img;
    private Button bt_camera;
    private Button bt_xiangce;
    private EditText etitle;
    private EditText econtent;
    private EditText eschool;
    private EditText etime;
    private String imgstring;
    private Spinner spinner;
    private List<String> sdata;
    private ArrayAdapter<String> arr_adapter;
    private String typename;
    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tieziedit);
        initView();
        etitle = (EditText) findViewById(R.id.edtitle);
        econtent = (EditText) findViewById(R.id.edcontent);
        eschool = (EditText) findViewById(R.id.edschool);
        etime = (EditText) findViewById(R.id.edtime);
        eschool.setText(userschool);
        eschool.setFocusableInTouchMode(false);
        etime.setFocusableInTouchMode(false);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        etime.setText(simpleDateFormat.format(date));
        spinner = (Spinner)findViewById(R.id.spinner);
        sdata=new ArrayList<String>();
        sdata.add("房屋租赁");
        sdata.add("车辆租赁");
        sdata.add("攻略求助");
        sdata.add("点评");
        //2、未下来列表定义一个数组适配器
        arr_adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,sdata);
        //3、为适配器设置下拉菜单的样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //4、将适配器配置到下拉列表上
        spinner.setAdapter(arr_adapter);
        //5、给下拉菜单设置监听事件
        spinner.setOnItemSelectedListener(this);
    }

    private void initView() {
        iv_img = (ImageView) findViewById(R.id.iv_img);

        bt_xiangce = (Button) findViewById(R.id.bt_xiangce);
//从SharedPreferences获取图片
        getBitmapFromSharedPreferences();

//        bt_camera.setOnClickListener(this);
        bt_xiangce.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.bt_camera:
//                // 激活相机
//                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                // 判断存储卡是否可以用，可用进行存储
//                if (hasSdcard()) {
//                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
//                    // 从文件中创建uri
//                    Uri uri = Uri.fromFile(tempFile);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                }
//                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
//                startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
//                break;
            case R.id.bt_xiangce:
                // 激活系统图库，选择一张图片
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setType("image/*");
                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
                startActivityForResult(intent1, PHOTO_REQUEST_GALLERY);
                break;
        }
    }
    /*
     * 判断sdcard是否被挂载
     */
    private boolean hasSdcard() {
        //判断ＳＤ卡手否是安装好的　　　media_mounted
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * 剪切图片
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }
        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
            // 从相机返回的数据
            if (hasSdcard()) {
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(tieziedit.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                /**
                 * 获得图片
                 */
                iv_img.setImageBitmap(bitmap);
                //保存到SharedPreferences
                saveBitmapToSharedPreferences(bitmap);
            }
            try {
                // 将临时文件删除
                tempFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //保存图片到SharedPreferences
    private void saveBitmapToSharedPreferences(Bitmap bitmap) {
        // Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        //第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        imgstring = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        //第三步:将String保持至SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("testSP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("image", imgstring);
        editor.commit();

        //上传头像
//        setImgByStr(imageString,"");
    }


//    /**
//     * 上传头像
//     * @param imgStr
//     * @param imgName
//     */
//    public  void setImgByStr(String imgStr, String imgName) {
//        String url = "http://appserver.1035.mobi/MobiSoft/User_upLogo";
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("img", imgStr);
//
//    }

    //从SharedPreferences获取图片
    private void getBitmapFromSharedPreferences(){
        SharedPreferences sharedPreferences=getSharedPreferences("testSP", Context.MODE_PRIVATE);
        //第一步:取出字符串形式的Bitmap
        String imageString=sharedPreferences.getString("image", "");
        //第二步:利用Base64将字符串转换为ByteArrayInputStream
        byte[] byteArray=Base64.decode(imageString, Base64.DEFAULT);
        if(byteArray.length==0){
            iv_img.setImageResource(R.mipmap.ic_launcher);
        }else{
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArray);

            //第三步:利用ByteArrayInputStream生成Bitmap
            Bitmap bitmap= BitmapFactory.decodeStream(byteArrayInputStream);
            iv_img.setImageBitmap(bitmap);
        }

    }
    public void edittiezi(View view) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(weburl + "servlet/adAddOneTieziServlet?sid="+userid+"&stitle="+etitle.getText().toString()+"&scontent="+econtent.getText().toString()+"&simg="+imgstring+"&stime="+etime.getText().toString()+"&styple="+typename+"&sschool="+userschool+"").build();
        System.out.println(imgstring);
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                finish();
                Looper.prepare();
                Toast.makeText(tieziedit.this,"发布成功",Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        });
    }
    public void edfh(View view){
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        typename = sdata.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
