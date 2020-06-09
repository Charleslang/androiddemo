package com.dysy.carttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.dysy.carttest.dialog.ShowExitDialog;

import java.text.NumberFormat;

public class ReadyPayActivity extends AppCompatActivity {
    private TextView readyPayTime, readyPayPrice, readyPayBtn, readyPayTopContent, readyPayCancel;
    private NumberFormat nf;
    private double cost;
    private String costStr;
    private CountDownTimer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_pay);
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);

        initView();
        startTimer();
    }

    public void initView(){
        readyPayTime = findViewById(R.id.ready_pay_time);
        readyPayPrice = findViewById(R.id.ready_pay_price);
        readyPayBtn = findViewById(R.id.ready_pay_btn);
        readyPayTopContent = findViewById(R.id.ready_pay_topcontent);
        readyPayCancel = findViewById(R.id.ready_pay_cancel);

        cost = getIntent().getDoubleExtra("cost", 0);
        costStr = nf.format(cost);

        readyPayTime.getPaint().setFakeBoldText(true);
        readyPayPrice.getPaint().setFakeBoldText(true);
        readyPayBtn.getPaint().setFakeBoldText(true);
        readyPayTopContent.getPaint().setFakeBoldText(true);

        readyPayPrice.setText(costStr);
        readyPayBtn.setText("确认支付" + costStr);

        readyPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
            }
        });
        readyPayCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowExitDialog showExitDialog = new ShowExitDialog();
                showExitDialog.show(ReadyPayActivity.this, "提示信息","订单未支付，确定取消？");
            }
        });
    }

    // 开启倒计时
    public void startTimer(){
        // 总共为15分钟，每秒触发一次
        timer = new CountDownTimer(900000, 1000) {
            String minStr, secondStr;
            @Override
            public void onTick(long millisUntilFinished) {
                long min = millisUntilFinished / 60000;
                long second = (millisUntilFinished % 60000) / 1000;

                if (min < 10) {
                    minStr = "0" + min;
                }else {
                    minStr = String.valueOf(min);
                }
                if (second < 10) {
                    secondStr = "0" + second;
                }else {
                    secondStr = String.valueOf(second);
                }

                readyPayTime.setText(minStr + ":" + secondStr);
            }

            @Override
            public void onFinish() {
                if (timer != null){
                    timer.cancel();
                    timer = null;
                }
            }
        };
        timer.start();
    }
}
