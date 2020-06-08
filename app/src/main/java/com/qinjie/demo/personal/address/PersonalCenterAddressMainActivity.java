package com.qinjie.demo.personal.address;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dysy.carttest.R;
import com.MyApplication;
import com.qinjie.demo.utils.HttpClient1;
import com.qinjie.demo.utils.ThreadPoolExecutorService;

import java.util.List;

public class PersonalCenterAddressMainActivity extends Activity {
    private RecyclerView person_center_address_recyclerview;
    private ImageView person_center_address_image_return;
    private TextView person_center_address_text_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center_address_main);
        person_center_address_recyclerview = findViewById(R.id.person_center_address_recyclerview);
        person_center_address_image_return = findViewById(R.id.person_center_address_image_return);
        person_center_address_text_add = findViewById(R.id.person_center_address_text_add);
        //返回按钮
        person_center_address_image_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回
                PersonalCenterAddressMainActivity.this.finish();
            }
        });
        //添加新的地址
        person_center_address_text_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalCenterAddressMainActivity.this,PersonalCenterAddressEditActivity.class );
                Bundle data = new Bundle();
                data.putString("addressId", "0");
                intent.putExtra("datas", data);
                startActivity(intent);
            }
        });
        person_center_address_recyclerview.setLayoutManager(new LinearLayoutManager(PersonalCenterAddressMainActivity.this));

        ThreadPoolExecutorService.add(new Runnable() {
            @Override
            public void run() {
                String res = HttpClient1.doGet(getString(R.string.server_path) + getString(R.string.interface_users_addresses) + "?pageSize=100&pageNum=1", ((MyApplication) getApplication()).getToken());
                JSONObject je = JSONObject.parseObject(res);
                Message message = new Message();
                Bundle bundle = new Bundle();
                if (je.get("code").equals(200)) {
                    bundle.putString("datas", je.get("datas") + "");
                    message.setData(bundle);
                    message.what = 0;
                    myHandler.sendMessage(message);
                } else {
                    bundle.putString("toast", "加载地址信息失败, 错误信息："+je.get("msg"));
                    message.setData(bundle);
                    myHandler.sendMessage(message);
                }

            }
        });

    }

    @SuppressLint("HandlerLeak")
    private final Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: //获取地址信息成功
                    List<TbAddress> l = JSONArray.parseArray(msg.getData().get("datas")+"" , TbAddress.class);
                    person_center_address_recyclerview.setAdapter(new PersonalCenterAddressMainAdapter(PersonalCenterAddressMainActivity.this, l));
                    break;
                default:
                    Toast.makeText(PersonalCenterAddressMainActivity.this, "" + msg.getData().get("toast"), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onResume() {

        super.onResume();
        onCreate(null);
    }
}
