package com.qinjie.demo.personal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.dysy.carttest.R;

import java.util.List;

class PersonalCenterOrder1HolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private List<String> names;
    private List<String> nums;
    public PersonalCenterOrder1HolderAdapter(Context mContext, List<String> names, List<String> nums) {
        this.mContext = mContext;
        this.names = names;
        this.nums = nums;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PersonalCenterOrder2Holder(LayoutInflater.from(mContext).inflate(R.layout.layout_my_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PersonalCenterOrder2Holder personalCenterOrder2Holder = (PersonalCenterOrder2Holder) holder;
        personalCenterOrder2Holder.getPersonal_center_my_order_count().setText(nums.get(position));
        personalCenterOrder2Holder.getPersonal_center_my_order_name().setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
}
