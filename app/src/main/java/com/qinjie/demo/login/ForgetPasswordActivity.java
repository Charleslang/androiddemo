package com.qinjie.demo.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.MyApplication;
import com.alibaba.fastjson.JSONObject;
import com.dysy.carttest.R;
import com.google.common.base.Strings;
import com.qinjie.demo.utils.ACache;
import com.qinjie.demo.utils.CommonUtils;
import com.qinjie.demo.utils.HttpClient1;
import com.qinjie.demo.utils.SendEmail_OK;
import com.qinjie.demo.utils.ThreadPoolExecutorService;

import java.util.HashMap;

public class ForgetPasswordActivity extends AppCompatActivity {
    /**
     * 更新按钮
     */
    private Button login_forget_button_update;
    /**
     * 获取验证码按钮
     */
    private Button login_forget_button_code;
    /**
     * 验证码
     */
    private EditText login_forget_edittext_code;
    /**
     * 邮箱
     */
    private EditText login_forget_edittext_email;
    /**
     * 密码
     */
    private EditText login_forget_edittext_password;
    /**
     * 再次输入密码
     */
    private EditText login_forget_edittext_repassword;
    /**
     * 返回，点击返回登陆
     */
    private ImageView login_forget_image_return;
    private ACache mCache = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mCache = ACache.get(this);

        login_forget_button_update = findViewById(R.id.login_forget_button_update);
        login_forget_button_code = findViewById(R.id.login_forget_button_code);
        login_forget_edittext_code = findViewById(R.id.login_forget_edittext_code);
        login_forget_edittext_email = findViewById(R.id.login_forget_edittext_email);
        login_forget_edittext_password = findViewById(R.id.login_forget_edittext_password);
        login_forget_edittext_repassword = findViewById(R.id.login_forget_edittext_repassword);
        login_forget_image_return = findViewById(R.id.login_forget_image_return);
        //返回按钮
        login_forget_image_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        login_forget_button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = null;
                String email = null;
                String password = null;
                String repassword = null;
                if(Strings.isNullOrEmpty(code = login_forget_edittext_code.getText().toString())){
                    Toast.makeText(ForgetPasswordActivity.this, "验证码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Strings.isNullOrEmpty(email = login_forget_edittext_email.getText().toString())){
                    Toast.makeText(ForgetPasswordActivity.this, "邮箱！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Strings.isNullOrEmpty(password = login_forget_edittext_password.getText().toString())){
                    Toast.makeText(ForgetPasswordActivity.this, "密码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Strings.isNullOrEmpty(repassword = login_forget_edittext_repassword.getText().toString())){
                    Toast.makeText(ForgetPasswordActivity.this, "在次输入密码！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.equals(repassword)){
                    Toast.makeText(ForgetPasswordActivity.this, "密码不一致！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!mCache.getAsString("forget_email"+code).equals(email)){
                    Toast.makeText(ForgetPasswordActivity.this, "邮箱不一致！", Toast.LENGTH_SHORT).show();
                }
                mCache.remove("forget_email" + code);
                final String finalPassword = password;
                final String finalEmail = email;
                ThreadPoolExecutorService.add(new Runnable() {
                    @Override
                    public void run() {
                        HashMap params = new HashMap<>();
                        params.put("email", finalEmail);
                        params.put("password", finalPassword);
                        String res = HttpClient1.doPut(getString(R.string.server_path) + getString(R.string.interface_user_forget), params, ((MyApplication) getApplication()).getToken());
                        JSONObject jsonObject2 = JSONObject.parseObject(res, JSONObject.class);
                        String toast = null;
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        if (jsonObject2.get("code").equals(200)) {
                            toast = "成功！";
                            message.what = 0;
                        } else {
                            toast = "错误：" + jsonObject2.get("msg");
                        }
                        bundle.putString("toast", toast);
                        message.setData(bundle);
                        myHandler.sendMessage(message);
                    }
                });
            }
        });
        //获取验证码响应函数
        login_forget_button_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否为正确的邮箱格式
                final String email = login_forget_edittext_email.getText().toString();
                if (CommonUtils.isEmail(email)) {
                    CountDownTimer timer = new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            login_forget_button_code.setEnabled(false);
                            login_forget_button_code.setText("已发送(" + millisUntilFinished / 1000 + ")");

                        }

                        @Override
                        public void onFinish() {
                            login_forget_button_code.setEnabled(true);
                            login_forget_button_code.setText("重新获取验证码");

                        }
                    }.start();

                    ThreadPoolExecutorService.add(new Runnable() {
                        @Override
                        public void run() {
                            //随机验证码
                            String toast = null;
                            String random = CommonUtils.getRandomString(6);
                            if (SendEmail_OK.sendMessage(email, email, "忘记密码", "您本次的验证码为：" + random + ",该验证码一个小时内有效", 1)) {
                                mCache.put("forget_email"+random, email, 3600);
                                toast = "发送成功请注意查收";
                            } else {
                                toast = "发送失败，请重试";
                            }

                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("toast", toast);
                            message.setData(bundle);
                            myHandler.sendMessage(message);
                        }
                    });
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "请输入正确格式的邮箱", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @SuppressLint("HandlerLeak")
    private final Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
                    Bundle bundle = msg.getData();
            switch (msg.what) {
                case 0: //密码更新成功
                    Toast.makeText(ForgetPasswordActivity.this, bundle.get("toast")+"", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(ForgetPasswordActivity.this, bundle.get("toast")+"", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
