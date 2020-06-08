package com.qinjie.demo.personal;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dysy.carttest.R;


public class PersonalCenterCountHolder extends RecyclerView.ViewHolder {
    private TextView personal_center_my_favorites;
    private TextView personal_center_my_bands;

    public TextView getPersonal_center_my_favorites() {
        return personal_center_my_favorites;
    }

    public void setPersonal_center_my_favorites(TextView personal_center_my_favorites) {
        this.personal_center_my_favorites = personal_center_my_favorites;
    }

    public TextView getPersonal_center_my_bands() {
        return personal_center_my_bands;
    }

    public void setPersonal_center_my_bands(TextView personal_center_my_bands) {
        this.personal_center_my_bands = personal_center_my_bands;
    }

    public PersonalCenterCountHolder(@NonNull View itemView) {
        super(itemView);
        personal_center_my_favorites = itemView.findViewById(R.id.personal_center_my_favorites);
        personal_center_my_bands = itemView.findViewById(R.id.personal_center_my_bands);
    }
}