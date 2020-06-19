package com.dysy.carttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class UserPayResultActivity extends AppCompatActivity {
    private TextView payResultSuccess, payResultTopContent, payResultZfcg, payResultMoney, payResultRmoney;
    private double cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pay_result);

        initView();
        initData();
    }

    public void initView(){

        payResultTopContent = findViewById(R.id.pay_result_topcontent);
        payResultSuccess = findViewById(R.id.pay_result_success);
        payResultZfcg = findViewById(R.id.pay_result_zfcg);
        payResultMoney = findViewById(R.id.pay_result_money);
        payResultRmoney = findViewById(R.id.pay_result_rmoney);

        payResultTopContent.getPaint().setFakeBoldText(true);
        payResultRmoney.getPaint().setFakeBoldText(true);
        payResultZfcg.getPaint().setFakeBoldText(true);

        payResultSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPayResultActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initData(){
        cost = getIntent().getDoubleExtra("cost", 0);
        DecimalFormat df = new DecimalFormat("#.00");
        payResultMoney.setText(df.format(cost) + "元");
        payResultRmoney.setText("- " + df.format(cost));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UserPayResultActivity.this, ShoppingCartActivity.class);
        startActivity(intent);
    }
}
