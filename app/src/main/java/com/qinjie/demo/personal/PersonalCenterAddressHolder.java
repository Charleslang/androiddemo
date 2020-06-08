package com.qinjie.demo.personal;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dysy.carttest.R;

public class PersonalCenterAddressHolder extends RecyclerView.ViewHolder{
    private TextView personal_center_address;
    public PersonalCenterAddressHolder(@NonNull View itemView) {
        super(itemView);
        personal_center_address = itemView.findViewById(R.id.personal_center_address);

    }

    public TextView getPersonal_center_address() {
        return personal_center_address;
    }

    public void setPersonal_center_address(TextView personal_center_address) {
        this.personal_center_address = personal_center_address;
    }
}
