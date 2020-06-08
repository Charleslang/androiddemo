package com.qinjie.demo.personal;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.dysy.carttest.R;

import java.util.List;

public class PersonalCenterOrder1Holder extends RecyclerView.ViewHolder {
    /**
     * 订单头部的布局，用来设置点击事件或者设置里面的内容
     */
    private LinearLayout personal_center_all_my_order;
    /**
     * 订单下面的图片，水平列表
     */
    private RecyclerView personal_center_my_order_recyclerView;

    public PersonalCenterOrder1Holder(@NonNull View itemView,List<String>names, List<String>nums) {
        super(itemView);
        //得到两个对象
        personal_center_all_my_order = itemView.findViewById(R.id.personal_center_all_my_order);
        personal_center_my_order_recyclerView = itemView.findViewById(R.id.personal_center_my_order_recyclerView);
        //设置布局水平滑动
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        personal_center_my_order_recyclerView.setLayoutManager(linearLayoutManager);

        //设置RecyclerView的适配器
        personal_center_my_order_recyclerView.setAdapter(new PersonalCenterOrder1HolderAdapter(itemView.getContext(), names, nums));


    }

    public LinearLayout getPersonal_center_all_my_order() {
        return personal_center_all_my_order;
    }

    public void setPersonal_center_all_my_order(LinearLayout personal_center_all_my_order) {
        this.personal_center_all_my_order = personal_center_all_my_order;
    }

    public RecyclerView getPersonal_center_my_order_recyclerView() {
        return personal_center_my_order_recyclerView;
    }

    public void setPersonal_center_my_order_recyclerView(RecyclerView personal_center_my_order_recyclerView) {
        this.personal_center_my_order_recyclerView = personal_center_my_order_recyclerView;
    }
}