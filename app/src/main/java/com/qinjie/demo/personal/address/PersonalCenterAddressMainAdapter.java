package com.qinjie.demo.personal.address;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.dysy.carttest.R;

import java.util.List;

import cn.carbs.android.avatarimageview.library.AvatarImageView;

public class PersonalCenterAddressMainAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private List<TbAddress> addressList;

    public PersonalCenterAddressMainAdapter(Context mContext, List<TbAddress> addressList) {
        this.mContext = mContext;
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PersonalCenterAddressMainHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_personal_center_address_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PersonalCenterAddressMainHolder personalCenterAddressMainHolder = (PersonalCenterAddressMainHolder) holder;
        final TbAddress address = addressList.get(position);
        personalCenterAddressMainHolder.getLayout_personal_center_address_name().setText(address.getaUsername());
        personalCenterAddressMainHolder.getLayout_personal_center_address_phone().setText(address.getaPhone());
        personalCenterAddressMainHolder.getLayout_personal_center_address_details().setText(address.getaDetails());
        personalCenterAddressMainHolder.getLayout_personal_center_address_edit().setTag(address.getaId());
        personalCenterAddressMainHolder.getLayout_personal_center_address_name1().setTextAndColor(address.getaUsername(), R.color.colorPrimaryDark);

        if(0==address.getaUserDefault()){
        personalCenterAddressMainHolder.getLayout_personal_center_address_default().setVisibility(View.INVISIBLE);
        }
        //进入编辑页面
        personalCenterAddressMainHolder.getLayout_personal_center_address_edit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,PersonalCenterAddressEditActivity.class );
                Bundle bu = new Bundle();
                bu.putString("addressId", address.getaId()+"");
                bu.putString("username", address.getaUsername()+"");
                bu.putString("details", address.getaDetails()+"");
                bu.putString("phone", address.getaPhone()+"");
                bu.putString("default", address.getaUserDefault()+"");
                intent.putExtra("datas", bu);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    class PersonalCenterAddressMainHolder extends RecyclerView.ViewHolder{
        private AvatarImageView layout_personal_center_address_name1;
        private TextView layout_personal_center_address_name;
        private TextView layout_personal_center_address_phone;
        private TextView layout_personal_center_address_default;
        private TextView layout_personal_center_address_details;
        private TextView layout_personal_center_address_edit;
        public PersonalCenterAddressMainHolder(@NonNull View itemView) {
            super(itemView);
            layout_personal_center_address_name = itemView.findViewById(R.id.layout_personal_center_address_name);
            layout_personal_center_address_phone = itemView.findViewById(R.id.layout_personal_center_address_phone);
            layout_personal_center_address_default = itemView.findViewById(R.id.layout_personal_center_address_default);
            layout_personal_center_address_details = itemView.findViewById(R.id.layout_personal_center_address_details);
            layout_personal_center_address_edit = itemView.findViewById(R.id.layout_personal_center_address_edit);
            layout_personal_center_address_name1 = itemView.findViewById(R.id.layout_personal_center_address_name1);
        }

        public AvatarImageView getLayout_personal_center_address_name1() {
            return layout_personal_center_address_name1;
        }

        public void setLayout_personal_center_address_name1(AvatarImageView layout_personal_center_address_name1) {
            this.layout_personal_center_address_name1 = layout_personal_center_address_name1;
        }

        public TextView getLayout_personal_center_address_name() {
            return layout_personal_center_address_name;
        }

        public void setLayout_personal_center_address_name(TextView layout_personal_center_address_name) {
            this.layout_personal_center_address_name = layout_personal_center_address_name;
        }

        public TextView getLayout_personal_center_address_phone() {
            return layout_personal_center_address_phone;
        }

        public void setLayout_personal_center_address_phone(TextView layout_personal_center_address_phone) {
            this.layout_personal_center_address_phone = layout_personal_center_address_phone;
        }

        public TextView getLayout_personal_center_address_default() {
            return layout_personal_center_address_default;
        }

        public void setLayout_personal_center_address_default(TextView layout_personal_center_address_default) {
            this.layout_personal_center_address_default = layout_personal_center_address_default;
        }

        public TextView getLayout_personal_center_address_details() {
            return layout_personal_center_address_details;
        }

        public void setLayout_personal_center_address_details(TextView layout_personal_center_address_details) {
            this.layout_personal_center_address_details = layout_personal_center_address_details;
        }

        public TextView getLayout_personal_center_address_edit() {
            return layout_personal_center_address_edit;
        }

        public void setLayout_personal_center_address_edit(TextView layout_personal_center_address_edit) {
            this.layout_personal_center_address_edit = layout_personal_center_address_edit;
        }
    }
}
