package com.dysy.carttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dysy.carttest.adapter.OrderItemAdapter;
import com.dysy.carttest.dto.GoodsDTO;
import com.dysy.carttest.dto.InsertOrderDTO;
import com.dysy.carttest.dto.OrderDetailsDTO;
import com.dysy.carttest.util.MyBigDecimal;
import com.dysy.carttest.util.OkHttpCallback;
import com.dysy.carttest.util.OkHttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrderActivity extends AppCompatActivity {
    private TextView orderBack;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private OrderItemAdapter orderItemAdapter;
    private SparseArray<GoodsDTO> selectedList;
    private Double cost;
    private TextView orderOrder, orderPaymoney, orderBpaymoney, orderTopContent, orderSubmit;
    private TextView orderName, orderTel, orderLocation;
    private NumberFormat nf;
    private List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();
    private InsertOrderDTO insertOrderDTO;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient okHttpClient = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);

        initDate();
        initView();
    }

    public void initView(){
        orderBack = findViewById(R.id.order_back_btn);
        orderOrder = findViewById(R.id.order_order);
        orderPaymoney = findViewById(R.id.order_paymoney);
        orderBpaymoney = findViewById(R.id.order_bpaymoney);
        orderTopContent = findViewById(R.id.order_topcontent);
        orderSubmit = findViewById(R.id.order_submit);
        orderLocation = findViewById(R.id.order_location);
        orderName = findViewById(R.id.order_name);
        orderTel = findViewById(R.id.order_tel);

        mRecyclerView = findViewById(R.id.rv_order_list);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new MyDecoration());
        orderItemAdapter = new OrderItemAdapter(this, selectedList);
        mRecyclerView.setAdapter(orderItemAdapter);

        orderOrder.setText("订单" + nf.format(cost + 2));
        orderPaymoney.setText("待支付" + nf.format(cost + 2));
        orderBpaymoney.setText("待支付" + nf.format(cost + 2));

        orderTopContent.getPaint().setFakeBoldText(true);
        orderPaymoney.getPaint().setFakeBoldText(true);
        orderSubmit.getPaint().setFakeBoldText(true);

        orderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        orderSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = orderName.getText().toString();
                String userTel = orderTel.getText().toString();
                String userAddr = orderLocation.getText().toString();
                if ("".equals(userName) || "".equals(userTel) || "".equals(userAddr)) {
                    Toast.makeText(OrderActivity.this,"请完善地址信息",Toast.LENGTH_SHORT).show();
                } else {
                    creatOrder();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Gson gsonObj = new Gson();
                            String json = gsonObj.toJson(insertOrderDTO);
                            RequestBody requestBody = RequestBody.create(JSON, json);
                            Request request = new Request.Builder()
                                    .url("http://192.168.43.131:8080/GCSJProject/order/insert")
                                    .post(requestBody)
                                    .build();
                            try (Response response = okHttpClient.newCall(request).execute()) {
                                Gson gson = new Gson();
                                Integer orderId = gson.fromJson(response.body().string().toString(), Integer.class);
                                if (orderId != 0) {
                                    Intent intent = new Intent(OrderActivity.this, ReadyPayActivity.class);
                                    intent.putExtra("cost",cost+2);
                                    intent.putExtra("orderId", orderId);
                                    Log.d("orderId-------",orderId + "*****************");
                                    startActivity(intent);
                                } else {
                                    Log.d("订单错误","---------------");
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }

    public void initDate() {
        Map<String, Object> map = (HashMap<String, Object>)getIntent().getSerializableExtra("map");
        selectedList = (SparseArray<GoodsDTO>) map.get("goodsList");
        cost = (Double) map.get("cost");
    }

    class MyDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0,0,0,getResources().getDimensionPixelOffset(R.dimen.order_item_decoration));
        }
    }

    // 创建订单
    public void creatOrder(){
        for (int i = 0; i < selectedList.size(); i++) {
            GoodsDTO goodsDTO = selectedList.valueAt(i);
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(goodsDTO.getgId(),goodsDTO.getgPrice(),
                    goodsDTO.getgName(),goodsDTO.getgPhoto(),goodsDTO.getSelectNum(),
                    goodsDTO.getgNumber() - goodsDTO.getSelectNum());
            orderDetailsDTOList.add(orderDetailsDTO);
        }
        float price = Float.parseFloat(MyBigDecimal.add(cost,2));
        Log.d("价格price---:" ,price + "------------------");
        Log.d("加法->",MyBigDecimal.add(cost,2));
        insertOrderDTO = new InsertOrderDTO(price, String.valueOf(1),1,"react",
                orderLocation.getText().toString(),
                "零食店铺(成信大店)",
                null,orderDetailsDTOList);
    }
}
