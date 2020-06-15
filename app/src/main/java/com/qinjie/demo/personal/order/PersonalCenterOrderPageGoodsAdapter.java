package com.qinjie.demo.personal.order;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dysy.carttest.R;

import java.util.List;


public class PersonalCenterOrderPageGoodsAdapter extends BaseAdapter {
    private Context mContext;
    private List<GoodsInfo> goodsInfo;
    private LayoutInflater layoutInflater;

    public PersonalCenterOrderPageGoodsAdapter(Context mContext, List<GoodsInfo> goodsInfo, LayoutInflater layoutInflater) {
        Log.e("GoodsInfo", goodsInfo.toString());
        this.mContext = mContext;
        this.layoutInflater = LayoutInflater.from(mContext);
        this.goodsInfo = goodsInfo;
    }

    @Override
    public int getCount() {
        return goodsInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return goodsInfo.get(position).getGoodsId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolde = new ViewHolder();
//        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.fragment_personal_center_order_page_goods_item, null);
            convertView.setTag(viewHolde);
            viewHolde.personal_center_order_page_list_goods_name = convertView.findViewById(R.id.personal_center_order_page_list_goods_name);
            viewHolde.personal_center_order_page_list_goods_price = convertView.findViewById(R.id.personal_center_order_page_list_goods_price);
            viewHolde.personal_center_order_page_list_goods_images = convertView.findViewById(R.id.personal_center_order_page_list_goods_images);
            viewHolde.personal_center_order_page_list_goods_num = convertView.findViewById(R.id.personal_center_order_page_list_goods_num);

//        }
//        else{
//            viewHolde = (ViewHolder)convertView.getTag();
//        }

        viewHolde.personal_center_order_page_list_goods_name.setText(goodsInfo.get(position).getGoodsName());
        viewHolde.personal_center_order_page_list_goods_price.setText(goodsInfo.get(position).getGoodsPrice());
        Glide.with(mContext).load(goodsInfo.get(position).getGoodsImage()).into(viewHolde.personal_center_order_page_list_goods_images);
        viewHolde.personal_center_order_page_list_goods_num.setText("X "+goodsInfo.get(position).getGoodsNum());

        return convertView;
    }

    private static class ViewHolder{
        /**
         * 商品图片
         */
        ImageView personal_center_order_page_list_goods_images;
        /**
         * 商品名
         */
        TextView personal_center_order_page_list_goods_name;
        /**
         * 商品单价
         */
        TextView personal_center_order_page_list_goods_price;
        /**
         * 商品数量
         */
        TextView personal_center_order_page_list_goods_num;

    }
}
