package com.qinjie.demo.personal;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dysy.carttest.R;


public class PersonalCenterWalletHolder extends RecyclerView.ViewHolder {
    private TextView personal_center_my_item_text;
    private ImageView personal_center_my_item_image;

    public TextView getPersonal_center_my_item_text() {
        return personal_center_my_item_text;
    }

    public void setPersonal_center_my_item_text(TextView personal_center_my_item_text) {
        this.personal_center_my_item_text = personal_center_my_item_text;
    }

    public ImageView getPersonal_center_my_item_image() {
        return personal_center_my_item_image;
    }

    public void setPersonal_center_my_item_image(ImageView personal_center_my_item_image) {
        this.personal_center_my_item_image = personal_center_my_item_image;
    }

    public PersonalCenterWalletHolder(@NonNull View itemView) {
        super(itemView);
        personal_center_my_item_text = itemView.findViewById(R.id.personal_center_my_item_text);
        personal_center_my_item_image = itemView.findViewById(R.id.personal_center_my_item_image);
    }
}