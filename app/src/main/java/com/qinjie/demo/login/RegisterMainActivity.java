package com.qinjie.demo.login;

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

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.dysy.carttest.R;
import com.qinjie.demo.utils.ACache;
import com.qinjie.demo.utils.CommonUtils;
import com.qinjie.demo.utils.HttpClient1;
import com.qinjie.demo.utils.SendEmail_OK;
import com.qinjie.demo.utils.ThreadPoolExecutorService;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;

public class RegisterMainActivity extends AppCompatActivity {
    /**
     * 登陆按钮
     */
    private Button login_register_button_register;
    /**
     * 获取验证码按钮
     */
    private Button login_register_button_code;
    /**
     * 验证码
     */
    private EditText login_register_edittext_code;
    /**
     * 邮箱
     */
    private EditText login_register_edittext_email;
    /**
     * 密码
     */
    private EditText login_register_edittext_password;
    /**
     * 再次输入密码
     */
    private EditText login_register_edittext_repassword;
    /**
     * 昵称
     */
    private EditText login_register_edittext_nickname;
    /**
     * 返回，点击返回登陆
     */
    private ImageView login_register_image_return;
    /**
     * 头像
     */
    private ImageView login_register_image_profile;
    /**
     * 加载动画
     */
    private AVLoadingIndicatorView login_register_view_load;


    private ACache mCache = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);
        //按钮
        login_register_button_register = findViewById(R.id.login_register_button_register);
        login_register_button_code = findViewById(R.id.login_register_button_code);
        //编辑框
        login_register_edittext_code = findViewById(R.id.login_register_edittext_code);
        login_register_edittext_email = findViewById(R.id.login_register_edittext_email);
        login_register_edittext_password = findViewById(R.id.login_register_edittext_password);
        login_register_edittext_repassword = findViewById(R.id.login_register_edittext_repassword);
        login_register_edittext_nickname = findViewById(R.id.login_register_edittext_nickname);
        //图片
        login_register_image_return = findViewById(R.id.login_register_image_return);
        login_register_image_profile = findViewById(R.id.login_register_image_profile);
        mCache = ACache.get(this);

        //返回按钮
        login_register_image_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterMainActivity.this.finish();
            }
        });

        //注册按钮响应函数
        login_register_button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = login_register_edittext_nickname.getText().toString();
                String password = login_register_edittext_password.getText().toString();
                String repassword = login_register_edittext_repassword.getText().toString();
                final String code = login_register_edittext_code.getText().toString();
                if (nickname.equals("")) {
                    Toast.makeText(RegisterMainActivity.this, "昵称！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断密码两次输入是否正确
                if (password.equals("")) {
                    Toast.makeText(RegisterMainActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(repassword)) {
                    Toast.makeText(RegisterMainActivity.this, "两次密码不同！", Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = null;
                if ((email = mCache.getAsString(code)) == null) {
                    Toast.makeText(RegisterMainActivity.this, "验证码错误！", Toast.LENGTH_SHORT).show();
                    return;
                }

                final HashMap<String, Object> params = new HashMap<>();
                params.put("nickname", nickname);
                params.put("gender", "男");
                params.put("password", password);
                params.put("email", email);

                final MyDialog myDialog = MyDialog.showDialog(RegisterMainActivity.this);
                myDialog.show();
                //新建线程，接口新增用户数据
                ThreadPoolExecutorService.add(new Runnable() {
                    @Override
                    public void run() {
                        String res = HttpClient1.doPost(getString(R.string.server_path) + getString(R.string.interface_user_register), params);
                        JSONObject jsonObject2 = JSONObject.parseObject(res, JSONObject.class);
                        String toast = null;
                        if (jsonObject2.get("code").equals(200)) {
                            mCache.remove(code);
                            toast = "注册成功";
                        } else {
                            toast = "错误：" + jsonObject2.get("msg");
                        }
                        myDialog.cancel();
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("toast", toast);
                        message.what = 0;
                        message.setData(bundle);
                        myHandler.sendMessage(message);

                    }
                });
            }
        });
        //获取验证码响应函数
        login_register_button_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否为正确的邮箱格式
                final String email = login_register_edittext_email.getText().toString();
                if (CommonUtils.isEmail(email)) {
                    CountDownTimer timer = new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            login_register_button_code.setEnabled(false);
                            login_register_button_code.setText("已发送(" + millisUntilFinished / 1000 + ")");

                        }

                        @Override
                        public void onFinish() {
                            login_register_button_code.setEnabled(true);
                            login_register_button_code.setText("重新获取验证码");

                        }
                    }.start();

                    ThreadPoolExecutorService.add(new Runnable() {
                        @Override
                        public void run() {
                            //随机验证码
                            String toast = null;
                            String random = CommonUtils.getRandomString(6);
                            if (SendEmail_OK.sendMessage(email, email, "注册验证码", "您本次的注册验证码为：" + random + ",该验证码一个小时内有效", 1)) {
                                mCache.put(random, email, 3600);
                                toast = "发送成功请注意查收";
                            } else {
                                toast = "发送失败，请重试";
                            }

                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("toast", toast);
                            message.what = 0;
                            message.setData(bundle);
                            myHandler.sendMessage(message);
                        }
                    });
                } else {
                    Toast.makeText(RegisterMainActivity.this, "请输入正确格式的邮箱", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @SuppressLint("HandlerLeak")
    private final Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: //信息提示
                    String toast = ""+msg.getData().get("toast");
                    Toast.makeText(RegisterMainActivity.this, toast, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    break;
                default:
                    break;
            }
        }
    };
}
