package com.dysy.carttest.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dysy.carttest.R;
import com.dysy.carttest.dto.GoodsDTO;

import java.text.NumberFormat;
import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderRecycle> {
    private Context mContext;
    private List mList;
    private SparseArray<GoodsDTO> selectList;
    private NumberFormat nf;

    public OrderItemAdapter(Context mContext, SparseArray<GoodsDTO> selectList) {
        this.mContext = mContext;
        this.selectList = selectList;
        // 对数字格式化，返回当前语言环境的格式1,000,000
        nf = NumberFormat.getCurrencyInstance();
        // 小数保留2位
        nf.setMaximumFractionDigits(2);
    }

    @NonNull
    @Override
    public OrderRecycle onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_order_item, parent, false);
        return new OrderRecycle(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRecycle holder, int position) {
        GoodsDTO goodsDTO = selectList.valueAt(position);
        holder.orderItemName.setText(goodsDTO.getgName());
        holder.orderItemNum.setText("x"+goodsDTO.getSelectNum().toString());
        holder.orderItemPrice.setText(nf.format(goodsDTO.getSelectNum() * goodsDTO.getgPrice()));
    }

    @Override
    public int getItemCount() {
        return selectList.size();
    }

    class OrderRecycle extends RecyclerView.ViewHolder {
        private TextView orderItemName, orderItemNum, orderItemPrice;

        public OrderRecycle(@NonNull View itemView) {
            super(itemView);
            orderItemName = itemView.findViewById(R.id.order_item_gname);
            orderItemNum = itemView.findViewById(R.id.order_item_gnum);
            orderItemPrice = itemView.findViewById(R.id.order_item_gprice);
        }
    }

}
