package com.qinjie.demo.personal.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.dysy.carttest.R;

import java.util.List;


public class PersonalCenterOrderPageAdapter extends BaseAdapter {

    public PersonalCenterOrderPageAdapter(Context context, List<OrderInfo> list) {
        this.mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
        mOrderInfoList = list;
    }

    private List<OrderInfo> mOrderInfoList;
    private Context mContext;
    private LayoutInflater layoutInflater;


    @Override
    public int getCount() {
        return mOrderInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mOrderInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mOrderInfoList.get(position).getOrderId();
    }


    private static class ViewHolder{
        /**
         * 商品图片
         */
        ImageView personal_center_order_page_list_goods_images;
        /**
         * 店铺名称
         */
        TextView personal_center_order_page_list_store;
        /**
         * 订单状态
         */
        TextView personal_center_order_page_list_order_status;
        /**
         * 商品名
         */
        TextView personal_center_order_page_list_goods_name;
        /**
         * 商品单价
         */
        TextView personal_center_order_page_list_goods_price;
        /**
         * 共计1件商品，合计100元
         */
        TextView personal_center_order_page_list_goods_goods_num_total_price;
        /**
         * 按钮删除
         */
        Button personal_center_order_page_list_button_delete;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolde = new ViewHolder();
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.fragment_personal_center_order_page_item, null);
            convertView.setTag(viewHolde);
            viewHolde.personal_center_order_page_list_goods_goods_num_total_price = convertView.findViewById(R.id.personal_center_order_page_list_goods_goods_num_total_price);
            viewHolde.personal_center_order_page_list_store = convertView.findViewById(R.id.personal_center_order_page_list_store);
            viewHolde.personal_center_order_page_list_order_status = convertView.findViewById(R.id.personal_center_order_page_list_order_status);
            viewHolde.personal_center_order_page_list_goods_name = convertView.findViewById(R.id.personal_center_order_page_list_goods_name);
            viewHolde.personal_center_order_page_list_goods_price = convertView.findViewById(R.id.personal_center_order_page_list_goods_price);
            viewHolde.personal_center_order_page_list_goods_images = convertView.findViewById(R.id.personal_center_order_page_list_goods_images);
            viewHolde.personal_center_order_page_list_button_delete = convertView.findViewById(R.id.personal_center_order_page_list_button_delete);

        }
        else{
            viewHolde = (ViewHolder)convertView.getTag();
        }
        viewHolde.personal_center_order_page_list_goods_goods_num_total_price.setText("共计"+mOrderInfoList.get(position).getGoodsNum()+"件商品，合计"+mOrderInfoList.get(position).getTotalPrice()+"元");
        viewHolde.personal_center_order_page_list_store.setText(mOrderInfoList.get(position).getStoreName());
        viewHolde.personal_center_order_page_list_order_status.setText(mOrderInfoList.get(position).getOrderStatus());
        viewHolde.personal_center_order_page_list_goods_name.setText(mOrderInfoList.get(position).getGoodsName());
        viewHolde.personal_center_order_page_list_goods_price.setText(mOrderInfoList.get(position).getGoodsPrice());
        Glide.with(mContext).load(mOrderInfoList.get(position).getGoodsImage()).into(viewHolde.personal_center_order_page_list_goods_images);
        viewHolde.personal_center_order_page_list_button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder3 = new AlertDialog.Builder(mContext);
                builder3.setTitle("删除订单？").setNegativeButton("取消", null)
                        .setPositiveButton("确认", null).setCancelable(false).show();
            }
        });
        return convertView;
    }
}
