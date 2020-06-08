package com.qinjie.demo.personal;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dysy.carttest.R;

public class PersonalCenterHeaderHolder extends RecyclerView.ViewHolder {
    private TextView personal_center__name;
    private TextView personal_center_mobile;
    private ImageView personal_center_profile;
    private ImageView personal_center_settings;

    public TextView getPersonal_center__name() {
        return personal_center__name;
    }

    public void setPersonal_center__name(TextView personal_center__name) {
        this.personal_center__name = personal_center__name;
    }

    public TextView getPersonal_center_mobile() {
        return personal_center_mobile;
    }

    public void setPersonal_center_mobile(TextView personal_center_mobile) {
        this.personal_center_mobile = personal_center_mobile;
    }

    public ImageView getPersonal_center_profile() {
        return personal_center_profile;
    }

    public void setPersonal_center_profile(ImageView personal_center_profile) {
        this.personal_center_profile = personal_center_profile;
    }

    public ImageView getPersonal_center_settings() {
        return personal_center_settings;
    }

    public void setPersonal_center_settings(ImageView personal_center_settings) {
        this.personal_center_settings = personal_center_settings;
    }

    public PersonalCenterHeaderHolder(@NonNull View itemView) {
        super(itemView);
        personal_center__name = itemView.findViewById(R.id.personal_center__name);
        personal_center_mobile = itemView.findViewById(R.id.personal_center_mobile);
        personal_center_profile = itemView.findViewById(R.id.personal_center_profile);
        personal_center_settings = itemView.findViewById(R.id.personal_center_settings);
    }
}