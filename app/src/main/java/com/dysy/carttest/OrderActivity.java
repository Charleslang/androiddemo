package com.dysy.carttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.dysy.carttest.adapter.OrderItemAdapter;
import com.dysy.carttest.dto.GoodsDTO;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {
    private TextView orderBack;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private OrderItemAdapter orderItemAdapter;
    private List mList = new ArrayList();
    private SparseArray<GoodsDTO> selectedList;
    private double cost;
    private TextView orderOrder, orderPaymoney, orderBpaymoney;
    private NumberFormat nf;


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

        mRecyclerView = findViewById(R.id.rv_order_list);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new MyDecoration());
        orderItemAdapter = new OrderItemAdapter(this, selectedList);
        mRecyclerView.setAdapter(orderItemAdapter);

        orderOrder.setText("订单" + nf.format(cost + 2));
        orderPaymoney.setText("待支付" + nf.format(cost + 2));
        orderBpaymoney.setText("待支付" + nf.format(cost + 2));

        orderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initDate() {
//        mList.add(1);
//        mList.add(2);
//        mList.add(3);
//        mList.add(4);
//        mList.add(5);
//        mList.add(6);
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
}
