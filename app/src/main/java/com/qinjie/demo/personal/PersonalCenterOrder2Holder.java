package com.qinjie.demo.personal;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dysy.carttest.R;


public class PersonalCenterOrder2Holder extends RecyclerView.ViewHolder {
    private ImageView personal_center_my_order_image;
    private TextView personal_center_my_order_name;
    private TextView personal_center_my_order_count;

    public ImageView getPersonal_center_my_order_image() {
        return personal_center_my_order_image;
    }

    public void setPersonal_center_my_order_image(ImageView personal_center_my_order_image) {
        this.personal_center_my_order_image = personal_center_my_order_image;
    }

    public TextView getPersonal_center_my_order_name() {
        return personal_center_my_order_name;
    }

    public void setPersonal_center_my_order_name(TextView personal_center_my_order_name) {
        this.personal_center_my_order_name = personal_center_my_order_name;
    }

    public TextView getPersonal_center_my_order_count() {
        return personal_center_my_order_count;
    }

    public void setPersonal_center_my_order_count(TextView personal_center_my_order_count) {
        this.personal_center_my_order_count = personal_center_my_order_count;
    }

    public PersonalCenterOrder2Holder(@NonNull View itemView) {
        super(itemView);
        personal_center_my_order_image = itemView.findViewById(R.id.personal_center_my_order_image);
        personal_center_my_order_name = itemView.findViewById(R.id.personal_center_my_order_name);
        personal_center_my_order_count = itemView.findViewById(R.id.personal_center_my_order_count);
    }

}