package com.qinjie.demo.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.MyApplication;
import com.alibaba.fastjson.JSONObject;
import com.dysy.carttest.R;
import com.qinjie.demo.main.MainMainActivity;
import com.qinjie.demo.utils.HttpClient1;
import com.qinjie.demo.utils.PersistenceToken;
import com.qinjie.demo.utils.ThreadPoolExecutorService;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.HashMap;

public class LoginMainActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 账号密码的edittext
     */
    private EditText login_edittext_password, login_edittext_email;
    /**
     * 登陆按钮
     */
    private Button login_main_button_login;
    /**
     * 注册
     */
    private TextView login_main_text_register;
    /**
     * 忘记密码
     */
    private TextView login_main_text_forget;


    private Tencent mTencent;
    private IUiListener listener;

    /**
     * qq登陆
     */
    private ImageView login_main_image_qq;
    /**
     * 微信登陆
     */
    private ImageView login_main_image_wechat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //存在token直接登陆
        String token = null;
        if(!(token = PersistenceToken.getToken(LoginMainActivity.this)).equals("")){
            ((MyApplication) getApplication()).setToken(token);
            //调用接口进行登陆token验证有效性，有效直接登陆
            ThreadPoolExecutorService.add(new Runnable() {
                @Override
                public void run() {
                    String res = HttpClient1.doGet(getString(R.string.server_path) + getString(R.string.interface_users_validate),  ((MyApplication) getApplication()).getToken());
                    JSONObject jsonObject = JSONObject.parseObject(res);
                    if(jsonObject.get("code").equals(200)){
                        Intent intent = new Intent(LoginMainActivity.this, MainMainActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_main);
        initView();
    }

    private void initView() {

        login_edittext_password = findViewById(R.id.login_edittext_password);
        login_edittext_email = findViewById(R.id.login_edittext_email);
        login_main_button_login = findViewById(R.id.login_main_button_login);
        login_main_image_qq = findViewById(R.id.login_main_image_qq);
        login_main_text_forget = findViewById(R.id.login_main_text_forgot);
//        login_main_image_wechat = findViewById(R.id.login_main_image_wechat);
        login_main_text_register = findViewById(R.id.login_main_text_register);
        login_main_text_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginMainActivity.this, RegisterMainActivity.class);
                startActivity(intent);
            }
        });
        //忘记密码
        login_main_text_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginMainActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        login_main_button_login.setOnClickListener(this);

        //qq登陆
        login_main_image_qq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener = new IUiListener() {
                    @Override
                    public void onComplete(Object o) {
                        Toast.makeText(LoginMainActivity.this, "ok", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginMainActivity.this, MainMainActivity.class));
                        JSONObject jsonObject = JSONObject.parseObject(o.toString());
                        //设置openid和access_token
                        final String openid = jsonObject.get("openid") + "";
                        String access_token = jsonObject.get("access_token") + "";
                        mTencent.setOpenId(openid);
                        mTencent.setAccessToken(access_token, jsonObject.get("expires_time") + "");
                        //获取用户信息并调用后台接口传递信息
                        final UserInfo info = new UserInfo(LoginMainActivity.this, mTencent.getQQToken());
                        ThreadPoolExecutorService.add(new Runnable() {
                            @Override
                            public void run() {
                                info.getUserInfo(new IUiListener() {
                                    @Override
                                    public void onComplete(Object o) {
                                        final HashMap<String, Object> params = new HashMap<>();
                                        //{"ret":0,"msg":"","is_lost":0,"nickname":"风飘遗落","gender":"男","gender_type":1,"province":"四川","city":"广安","year":"1998","constellation":"","figureurl":"http:\/\/qzapp.qlogo.cn\/qzapp\/1110443089\/E18718A94416B5A6FDDA11E5832BBF20\/30","figureurl_1":"http:\/\/qzapp.qlogo.cn\/qzapp\/1110443089\/E18718A94416B5A6FDDA11E5832BBF20\/50","figureurl_2":"http:\/\/qzapp.qlogo.cn\/qzapp\/1110443089\/E18718A94416B5A6FDDA11E5832BBF20\/100","figureurl_qq_1":"http:\/\/thirdqq.qlogo.cn\/g?b=oidb&k=F53kjR8VJd3AVVHgb2Owdw&s=40&t=1557219823","figureurl_qq_2":"http:\/\/thirdqq.qlogo.cn\/g?b=oidb&k=F53kjR8VJd3AVVHgb2Owdw&s=100&t=1557219823","figureurl_qq":"http:\/\/thirdqq.qlogo.cn\/g?b=oidb&k=F53kjR8VJd3AVVHgb2Owdw&s=640&t=1557219823","figureurl_type":"1","is_yellow_vip":"0","vip":"0","yellow_vip_level":"0","level":"0","is_yellow_year_vip":"0"}
                                        JSONObject jsonObject1 = JSONObject.parseObject(o.toString());
                                        params.put("openid", openid);
                                        params.put("nickname", jsonObject1.get("nickname"));
                                        params.put("gender", jsonObject1.get("gender"));
                                        params.put("profile", jsonObject1.get("figureurl_qq_1"));
                                        ThreadPoolExecutorService.add(new Runnable() {
                                            @Override
                                            public void run() {
                                                String res = HttpClient1.doPost(getString(R.string.server_path) + getString(R.string.interface_user_login_qq), params);
                                                JSONObject jsonObject2 = JSONObject.parseObject(res);
                                                //发送消息登陆成功
                                                Message message = new Message();
                                                Bundle bundle = new Bundle();
                                                if(jsonObject2.get("code").equals(200)){
                                                    message.what = 3;
                                                    //持久化token
                                                    PersistenceToken.saveToken(LoginMainActivity.this, jsonObject2.get("datas") + "");
                                                    ((MyApplication) getApplication()).setToken(jsonObject2.get("datas") + "");
                                                    bundle.putString("toast", "ok");
                                                }else{
                                                    bundle.putString("toast", jsonObject2.get("msg")+"");
                                                }
                                                message.setData(bundle);

                                                myHandler.sendMessage(message);
                                            }
                                        });
                                    }

                                    @Override
                                    public void onError(UiError uiError) {

                                    }

                                    @Override
                                    public void onCancel() {

                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onError(UiError uiError) {

                    }

                    @Override
                    public void onCancel() {

                    }
                };

                // Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
                // 其中APP_ID是分配给第三方应用的appid，类型为String。
                mTencent = Tencent.createInstance("1110443089", getApplicationContext());
                // 1.4版本:此处需新增参数，传入应用程序的全局context，可通过activity的getApplicationContext方法获取
                mTencent.login(LoginMainActivity.this, "all", listener);
            }
        });
    }

    //账号密码登陆
    @Override
    public void onClick(View v) {
        String email = login_edittext_email.getText().toString();
        String password = login_edittext_password.getText().toString();
        if ("".equals(email) || "".equals(password)) {
            Toast.makeText(LoginMainActivity.this, "邮箱、密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        final MyDialog myDialog = MyDialog.showDialog(LoginMainActivity.this);
        myDialog.show();
        final HashMap params = new HashMap();
        params.put("email", email);
        params.put("password", password);
        //进行调用后台接口验证
        ThreadPoolExecutorService.add(new Runnable() {
            @Override
            public void run() {
                try{
                    String res = HttpClient1.doPost(getString(R.string.server_path) + getString(R.string.interface_user_login), params);
                    JSONObject jsonObject = JSONObject.parseObject(res);
                    myDialog.cancel();
                    Message message = new Message();
                    String toast =null;
                    if (jsonObject.get("code").equals(200)) {
                        //登陆成功
                        message.what = 0;
                        ((MyApplication) getApplication()).setToken(jsonObject.get("datas") + "");
                        //持久化token
                        PersistenceToken.saveToken(LoginMainActivity.this, jsonObject.get("datas") + "");
                        toast = "登陆成功";
                    } else{
                        //错误提示
                        message.what = 1;
                        toast = jsonObject.get("msg") + "";
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("toast", toast);
                    message.setData(bundle);
                    myHandler.sendMessage(message);

                }catch (Exception e){
                    Log.e("EOORR", e.getMessage());
                    myDialog.cancel();
                }

            }
        });
    }


    //qq登陆回调
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, listener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @SuppressLint("HandlerLeak")
    private final Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: //登陆成功
                    String toast = ""+msg.getData().get("toast");
                    Toast.makeText(LoginMainActivity.this, toast, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginMainActivity.this, MainMainActivity.class);
                    startActivity(intent);
                    break;
                case 1: //错误
                    String toast1 = ""+msg.getData().get("toast");
                    Toast.makeText(LoginMainActivity.this, toast1, Toast.LENGTH_SHORT).show();

                    break;
                case 2: //自动登陆时token到期

                    break;
                case 3:
                    //qq登陆成功
                    break;
                default:
                    break;
            }
        }
    };
}
