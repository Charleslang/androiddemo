package com.dysy.carttest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.dysy.carttest.dialog.ExitDialog;
import com.dysy.carttest.pay.AuthResult;
import com.dysy.carttest.pay.H5PayDemoActivity;
import com.dysy.carttest.pay.PayDemoActivity;
import com.dysy.carttest.pay.PayResult;
import com.dysy.carttest.util.OkHttpCallback;
import com.dysy.carttest.util.OkHttpUtil;
import com.dysy.carttest.util.OrderInfoUtil2_0;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.Map;

public class ReadyPayActivity extends AppCompatActivity {
    private TextView readyPayTime, readyPayPrice, readyPayBtn, readyPayTopContent, readyPayCancel;
    private NumberFormat nf;
    private double cost;
    private String costStr;
    private CountDownTimer timer = null;
    private ExitDialog exitDialog;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private static final int PAY_ORDER_FLAG = 3;
    private String orderStr = "";
    private Integer orderId;

    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2016101200667109";

    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public static final String PID = "2088102179202860";

    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     */
    public static final String TARGET_ID = "";

    /**
     *  pkcs8 格式的商户私钥。
     *
     * 	如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
     * 	使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
     * 	RSA2_PRIVATE。
     *
     * 	建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
     * 	工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCL37SZI545lIL7jcKi6NhK8ytNaoA/yQiSmPWAdy5zDSLY8KOV0xVEHvEfME1HIYD9AwNCH8secDBKS2SUQbUb6+aqZYrtdyQ79n40Rz8QJkFLgkoJ7ReCmbbUbcqoi8ScbFfaYVumSTj5EYh/Ut2ETcReRwGcYtrJvhmJVuUdAX63RPPYLDr8k1aBZE81cyuqI1tGvnhqC7ouHdCddbdvpc7pyuw5UqwahktL/b4d/VDizietHBi2h2yxImHFtdPZx0iNJfYk5LJlaeDmph3lQNNDg7CPbuF+qXE6WL2hsCGxwVavXl2KUIBjowJxBLtFvpvasnaACKHaD5J1FbLxAgMBAAECggEAQ71cXknkp+gnoyIdX/46OdoYX46ze7CliTGwu718blWvSbcI0ld+1hXSGpOu6ULtkBr0agKxwRT+HgzzMeW8i0As3tr8dFKd5rhSmIjknrKYLrRznOkI2MQOykQ9wHCoEEVcKrO5ygduIxl9rIP6Kcfr0Oi8e/tYEBoTjfLaVLgVYTq8f56tRWfKTFJlHvt0FKMVUuYanvYL4X1wgWbpVBR6WnFksQmKRv+ex2Xp3M6ZqA0I1UwU6jbMRd4RuDZ+jouCs+uLIe7jg+TlUQFx7EskhNiBRirQa7TMZ8CDX/Ojv2ujBreMnkr1nHYj49puXqORbFYuiCPhFwlgINopvQKBgQD5EhqdX4mo3HnCyoaVC9JQuIUMxpmEupUDOj8IgiK1mc4HPxL9sYfPn8wPshkzRcLH135sJJf7MdoVlxplwjlZ8JCHvEsopi3TRsHzENiidYd0kWS1b+3lF888evoT/cb4ylvfY4JeYSDQMzHVgp/Ab3o7+6hnB4pi7e+aIZlL8wKBgQCPw+UkwkhGc5afj+L7hlLkDrPJQQB9h3faa+9ZTNxm5lKmkMXcO9fWKNgswHtg56FtRLfYvq/EAHT9HUl5UDbezju4PxqcWKbXWqmAC2K3R1JxpHUXHz66lKweA4KcYFoEyOmi7euqdIxfesUzuQEhtJfKVRPDvxkEsCTZNuwyiwKBgGLfbPFMG7mMzEknXIWOS87/cNHBhO/LoZvGY39dKaothDwcLc3Hy8IADWGtsZNI/imWwfjsDekJwSkQ8smycFHjzXwUo0FftpFIZXW4GkdjoIIFtZTQ+f8XlIzNxLhq5bGdoxrqxbXxmLUNnZrEX+ZkWoG1RQShWa4sBhGJhPFBAoGBAIBZtmQhCAHOBHWkMlO40pxE/AH7hIKNxVU988xYMJW1n6iRq/Q5gTLz+DvMBNq5MYeqSDC73ZQxjEDGOuiR0ssMeIi/3fQjuaCekqF8iL90b4iB+P+Rp6FQRnHtVFhkNTn8XYpWu4XM094Sb6kDzJzY7IN5fVHPdHiZAezjefCNAoGBAOeOSXOjUMFWoVlo/YFvw7oTg7E6UhMEi3NLPzaykaS15d6+WVE4rq43RYAWG5nwOcjNIoCncKBjZ6U4uqDrQmCiZ/qKK5xPE0ZrG+EtP8sNlgdRWHiao8QfH7ctMVzSdNJWboM7on1UEg/6m2awsSe5ctLxGKFZTuAac4XAfsJa";
    public static final String RSA_PRIVATE = "";

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Intent intent = new Intent(ReadyPayActivity.this, UserPayResultActivity.class);
                        intent.putExtra("cost",cost);
                        startActivity(intent);

                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        showAlert(ReadyPayActivity.this, getString(R.string.pay_success) + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(ReadyPayActivity.this, getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        showAlert(ReadyPayActivity.this, getString(R.string.auth_success) + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        showAlert(ReadyPayActivity.this, getString(R.string.auth_failed) + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_pay);
        // 配置沙箱环境
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
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
        orderId = getIntent().getIntExtra("orderId",0);
//        Log.d("支付界面收到的id->",orderId + "********************");
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
                OkHttpUtil.get(getString(R.string.server_path_djf) + "/alipay/pay?orderId=" + orderId,new OkHttpCallback(){
                    @Override
                    public void onFinish(String status, String mes) {
//                        Gson gson = new Gson();
//                        orderStr = gson.fromJson(mes, String.class);
                        orderStr = mes;
                        if (!"".equals(orderStr)){
                            if (timer != null) {
                                timer.cancel();
                                timer = null;
                            }
                            pay(readyPayBtn, orderStr);
                        } else {
                            Looper.prepare();
                            Toast.makeText(ReadyPayActivity.this, "获取订单信息失败",Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                    }
                });

            }
        });
        readyPayCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrderNotice();
            }
        });
    }

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

    /**
     * 支付宝支付业务示例
     */
    public void pay(View v, String orderStr) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(this, getString(R.string.error_missing_appid_rsa_private));
            return;
        }

        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
//        final String orderInfo = orderParam + "&" + sign;
        final String orderInfo = orderStr;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(ReadyPayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
    }

    private static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

    private static String bundleToString(Bundle bundle) {
        if (bundle == null) {
            return "null";
        }
        final StringBuilder sb = new StringBuilder();
        for (String key: bundle.keySet()) {
            sb.append(key).append("=>").append(bundle.get(key)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public void onBackPressed() {
        cancelOrderNotice();
    }

    public void cancelOrderNotice(){
        exitDialog = new ExitDialog(ReadyPayActivity.this);
        exitDialog.setTitle("提示消息");
        exitDialog.setMessage("订单未支付，确定取消支付？");
        exitDialog.setYesOnclickListener("确定", new ExitDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                exitDialog.dismiss();
                Intent itent = new Intent(ReadyPayActivity.this, ShoppingCartActivity.class);
                ReadyPayActivity.this.startActivity(itent);
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
