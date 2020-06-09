package com.dysy.carttest.dialog;

import android.content.Context;
import android.content.Intent;

import com.dysy.carttest.ShoppingCartActivity;

public class ShowExitDialog {
    private Context globalContext;
    private ExitDialog exitDialog;

    public void show(Context context, String title, String message){
        globalContext = context;
        exitDialog = new ExitDialog(globalContext);
        exitDialog.setTitle(title);
        exitDialog.setMessage(message);
        exitDialog.setYesOnclickListener("确定", new ExitDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                exitDialog.dismiss();
                Intent itent = new Intent(globalContext, ShoppingCartActivity.class);
                globalContext.startActivity(itent);
            }
        });
        exitDialog.setNoOnclickListener("取消", new ExitDialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                exitDialog.dismiss();
            }
        });
        exitDialog.show();

    }
}
