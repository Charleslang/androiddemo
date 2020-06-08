package com.qinjie.demo.personal.address;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.dysy.carttest.R;
import com.MyApplication;
import com.qinjie.demo.utils.HttpClient1;
import com.qinjie.demo.utils.ThreadPoolExecutorService;

import java.util.HashMap;

public class PersonalCenterAddressEditActivity extends AppCompatActivity {
    private ImageView person_center_address_edit_return;
    private EditText person_center_address_edit_username;
    private EditText person_center_address_edit_phone;
    private EditText person_center_address_edit_details;
    private Button person_center_address_edit_default;
    private Button person_center_address_edit_save;
    private Button person_center_address_edit_delete;

    private Integer addressId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center_address_edit);
        person_center_address_edit_return = findViewById(R.id.person_center_address_edit_return);
        person_center_address_edit_username = findViewById(R.id.person_center_address_edit_username);
        person_center_address_edit_phone = findViewById(R.id.person_center_address_edit_phone);
        person_center_address_edit_details = findViewById(R.id.person_center_address_edit_details);
        person_center_address_edit_default = findViewById(R.id.person_center_address_edit_default);
        person_center_address_edit_save = findViewById(R.id.person_center_address_edit_save);
        person_center_address_edit_delete = findViewById(R.id.person_center_address_edit_delete);
        //标识新增
        Bundle bundle = getIntent().getBundleExtra("datas");
        if((addressId = Integer.valueOf(bundle.get("addressId")+""))==0){
            person_center_address_edit_default.setEnabled(false);
        }else{
            //标识更新,设置现在的内容
            person_center_address_edit_username.setText(bundle.get("username")+"");
            person_center_address_edit_phone.setText(bundle.get("phone")+"");
            person_center_address_edit_details.setText(bundle.get("details")+"");
            if(bundle.get("default").equals("1")){
                //标识是默认地址
                person_center_address_edit_default.setEnabled(false);
            }
        }

        //返回
        person_center_address_edit_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalCenterAddressEditActivity.this.finish();
            }
        });
        //设置默认
        person_center_address_edit_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadPoolExecutorService.add(new Runnable() {
                    @Override
                    public void run() {
                        HashMap parms = new HashMap();
                        parms.put("addressId", addressId);
                        String res = HttpClient1.doPut(getString(R.string.server_path) + getString(R.string.interface_users_addresses_default), parms,((MyApplication) getApplication()).getToken());
                        Log.e("RESULT", res);
                        JSONObject jsonObject = JSONObject.parseObject("" + res);
                        String toast = null;
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        if (jsonObject.get("code").equals(200)) {
                            toast = "设置成功";
                            //设置成功后禁用设置默认按钮
                            message.what = 2;
                        } else {
                            toast = "设置错误，错误信息：" + jsonObject.get("msg");
                        }
                        bundle.putString("toast", toast);
                        message.setData(bundle);
                        myHandler.sendMessage(message);
                    }
                });
            }
        });

        //删除
        person_center_address_edit_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadPoolExecutorService.add(new Runnable() {
                    @Override
                    public void run() {
                        HashMap parms = new HashMap();
                        parms.put("addressId", addressId);
                        String res = HttpClient1.doDelete(getString(R.string.server_path) + getString(R.string.interface_users_addresses), ((MyApplication) getApplication()).getToken(), parms);
                        JSONObject jsonObject = JSONObject.parseObject("" + res);
                        String toast = null;
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        if (jsonObject.get("code").equals(200)) {
                            message.what = 1;
                            toast = "删除成功！";
                        } else {
                            toast = "删除失败，错误信息：" + jsonObject.get("msg");
                        }
                        bundle.putString("toast", toast);
                        message.setData(bundle);
                        myHandler.sendMessage(message);
                    }
                });
            }
        });

        //保存
        person_center_address_edit_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadPoolExecutorService.add(new Runnable() {
                    @Override
                    public void run() {
                        HashMap parms = new HashMap();
                        parms.put("username", person_center_address_edit_username.getText().toString());
                        parms.put("details", person_center_address_edit_details.getText().toString());
                        parms.put("phone", person_center_address_edit_phone.getText().toString());
                        String res;
                        if (addressId == 0) {
                            res = HttpClient1.doPost(getString(R.string.server_path) + getString(R.string.interface_users_addresses), parms, ((MyApplication) getApplication()).getToken());
                        } else {
                            parms.put("addressId", addressId);
                            res = HttpClient1.doPut(getString(R.string.server_path) + getString(R.string.interface_users_addresses), parms, ((MyApplication) getApplication()).getToken());
                        }
                        JSONObject jsonObject = JSONObject.parseObject("" + res);
                        String toast = null;
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        if (jsonObject.get("code").equals(200)) {
                            if(addressId==0){
                                message.what = 0;
                                toast = "添加成功！";
                                Log.e("MESSAGE", addressId+"");
                            }else{
                                message.what = 4;
                                toast = "保存成功！";
                            }
                        } else {
                            toast = "保存错误，错误信息：" + jsonObject.get("msg");
                        }
                        bundle.putString("toast", toast);
                        message.setData(bundle);
                        myHandler.sendMessage(message);
                    }
                });
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private final Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: //添加成功
                    Toast.makeText(PersonalCenterAddressEditActivity.this, "" + msg.getData().get("toast"), Toast.LENGTH_SHORT).show();
                    PersonalCenterAddressEditActivity.this.finish();
                    break;
                case 1: //删除成功
                    Toast.makeText(PersonalCenterAddressEditActivity.this, "" + msg.getData().get("toast"), Toast.LENGTH_SHORT).show();
                    PersonalCenterAddressEditActivity.this.finish();
                    break;
                case 2:
                    //设置成功
                    person_center_address_edit_default.setEnabled(false);
                    Toast.makeText(PersonalCenterAddressEditActivity.this, "" + msg.getData().get("toast"), Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    //保存成功
                    Toast.makeText(PersonalCenterAddressEditActivity.this, "" + msg.getData().get("toast"), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(PersonalCenterAddressEditActivity.this, "" + msg.getData().get("toast"), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
