package com.qinjie.demo.personal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.dysy.carttest.R;
import com.qinjie.demo.personal.address.PersonalCenterAddressMainActivity;
import com.qinjie.demo.personal.order.PersonalCenterOrderMainActivity;
import com.qinjie.demo.utils.HttpClient1;
import com.qinjie.demo.utils.ThreadPoolExecutorService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PersonalCenterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int mSize;
    private Context mContext;
    private HashMap<String, String> mParams;
    private View layout_edit_person_info;
    private Handler mHandler;
    private String token;

    public HashMap<String, String> getmParams() {
        return mParams;
    }

    public void setmParams(HashMap<String, String> mParams) {
        this.mParams = mParams;
    }

    public PersonalCenterAdapter(Context context, int size, HashMap<String, String> params, View layout_edit_person_info, Handler handler, String token) {
        this.mSize = size;
        this.mContext = context;
        this.mParams = params;
        this.layout_edit_person_info = layout_edit_person_info;
        this.mHandler = handler;
        this.token = token;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:

                return new PersonalCenterHeaderHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_my_header, parent, false));
            case 1:
                return new PersonalCenterCountHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_my_count, parent, false));
            case 2:
                List<String> names = new ArrayList<>(10);
                List<String> nums = new ArrayList<>(10);
                names.add("全部");
                names.add("已完成");
                names.add("未完成");
                nums.add("0");
                nums.add("0");
                nums.add("0");
                PersonalCenterOrder1Holder personalCenterOrder1Holder =
                        new PersonalCenterOrder1Holder(LayoutInflater.from(mContext).inflate(R.layout.layout_my_order_header, parent, false), names, nums);
                personalCenterOrder1Holder.getPersonal_center_all_my_order().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, PersonalCenterOrderMainActivity.class);
                        mContext.startActivity(intent);
                    }
                });
                return personalCenterOrder1Holder;
            case 3:
                return new PersonalCenterWalletHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_my_item, parent, false));
            case 4:
                return new PersonalCenterWalletHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_my_item, parent, false));
            case 5:
                return new PersonalCenterWalletHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_my_item, parent, false));
            case 6:
                return new PersonalCenterWalletHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_my_item, parent, false));
            case 7:
                return new PersonalCenterAddressHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_personal_center_address, parent, false));
            default:
                return new PersonalCenterHeaderHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_my_header, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 0:

                PersonalCenterHeaderHolder personalCenterHeaderHolder = (PersonalCenterHeaderHolder) holder;
                if (mParams.get("nickname") != null) {
                    personalCenterHeaderHolder.getPersonal_center__name().setText(mParams.get("nickname"));
                }
                if (mParams.get("gender") != null) {
                    personalCenterHeaderHolder.getPersonal_center_mobile().setText(mParams.get("gender"));
                }
                //圆形图片
                if (mParams.get("profile") != null) {
                    Glide.with(mContext).load(mParams.get("profile"))
                            .apply(RequestOptions.bitmapTransform(new CircleCrop())).into(personalCenterHeaderHolder.getPersonal_center_profile());
                }
                //设置设置按钮的响应函数
                //得到将要设置到AlertDialog中的view
                //把view放到AlertDialog
                AlertDialog.Builder builder3 = new AlertDialog.Builder(mContext);
                builder3 = builder3.setTitle("个人信息修改").setView(layout_edit_person_info).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog alertDialog1 = (AlertDialog) dialog;
                        Spinner layout_edit_person_info_gender = alertDialog1.findViewById(R.id.layout_edit_person_info_gender);
                        ImageView layout_edit_person_info_profile = alertDialog1.findViewById(R.id.layout_edit_person_info_profile);
                        EditText layout_edit_person_info_nickname = alertDialog1.findViewById(R.id.layout_edit_person_info_nickname);
                        EditText layout_edit_person_info_password = alertDialog1.findViewById(R.id.layout_edit_person_info_password);
                        Toast.makeText(mContext, layout_edit_person_info_gender.getSelectedItem().toString() + "", Toast.LENGTH_SHORT).show();
                        final HashMap<String, Object> params = new HashMap();
                        params.put("nickname", layout_edit_person_info_nickname.getEditableText());
                        params.put("password", layout_edit_person_info_password.getText().toString());
                        params.put("gender", layout_edit_person_info_gender.getSelectedItem().toString());
                        params.put("profile", layout_edit_person_info_profile.getTag());
                        ThreadPoolExecutorService.add(new Runnable() {
                            @Override
                            public void run() {
                                String res = HttpClient1.doPost(mContext.getString(R.string.server_path) + mContext.getString(R.string.interface_user_info_update), params, token);
                                JSONObject jsonObject2 = JSONObject.parseObject(res, JSONObject.class);
                                String toast = null;
                                if (jsonObject2.get("code").equals(200)) {
                                    toast = "修改成功！！";
                                } else {
                                    toast = "错误：" + jsonObject2.get("msg");
                                }
                                //修改个人信息的消息
                                Message message = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putString("toast", toast);
                                message.what = 2;
                                message.setData(bundle);
                                mHandler.sendMessage(message);
                            }
                        });
                    }
                })
                        .setCancelable(false).setNegativeButton("取消", null);
                final AlertDialog alertDialog = builder3.create();
                personalCenterHeaderHolder.getPersonal_center_settings().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.show();
                    }
                });
                break;
            case 1:
                PersonalCenterCountHolder personalCenterCountHolder = (PersonalCenterCountHolder) holder;
                personalCenterCountHolder.getPersonal_center_my_bands().setText("收藏");
                personalCenterCountHolder.getPersonal_center_my_favorites().setText("关注");
                break;
            case 2:
                //订单部分，在初始化时设置了，所以在这里没有考虑
                break;
            case 3:
                PersonalCenterWalletHolder personalCenterWalletHolder = (PersonalCenterWalletHolder) holder;
                personalCenterWalletHolder.getPersonal_center_my_item_image().setImageResource(R.drawable.ic_wallet);
                personalCenterWalletHolder.getPersonal_center_my_item_text().setText("我的钱包");
                break;
            case 4:
                PersonalCenterWalletHolder personalCenterWalletHolder2 = (PersonalCenterWalletHolder) holder;
                personalCenterWalletHolder2.getPersonal_center_my_item_image().setImageResource(R.drawable.ic_authentication);
                personalCenterWalletHolder2.getPersonal_center_my_item_text().setText("认证中心");
                break;
            case 5:
                PersonalCenterWalletHolder personalCenterWalletHolder3 = (PersonalCenterWalletHolder) holder;
                personalCenterWalletHolder3.getPersonal_center_my_item_image().setImageResource(R.drawable.ic_inform);
                personalCenterWalletHolder3.getPersonal_center_my_item_text().setText("系统设置");
                break;
            case 6:
                PersonalCenterWalletHolder personalCenterWalletHolder4 = (PersonalCenterWalletHolder) holder;
                personalCenterWalletHolder4.getPersonal_center_my_item_image().setImageResource(R.drawable.ic_set);
                personalCenterWalletHolder4.getPersonal_center_my_item_text().setText("安全与设置");
                break;
            case 7:
                PersonalCenterAddressHolder personalCenterAddressHolder = (PersonalCenterAddressHolder) holder;
                personalCenterAddressHolder.getPersonal_center_address().setText("我的地址");
                personalCenterAddressHolder.getPersonal_center_address().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, PersonalCenterAddressMainActivity.class);
                        mContext.startActivity(intent);
                    }
                });
                break;
            default:

        }

    }

    @Override
    public int getItemCount() {
        return mSize;
    }
}