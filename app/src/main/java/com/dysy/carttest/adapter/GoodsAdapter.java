package com.dysy.carttest.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dysy.carttest.GoodsItem;
import com.dysy.carttest.R;
import com.dysy.carttest.ShoppingCartActivity;
import com.dysy.carttest.dto.GoodsDTO;

import java.text.NumberFormat;
import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;


public class GoodsAdapter extends BaseAdapter implements StickyListHeadersAdapter {

//    private ArrayList<GoodsItem> dataList;
    private ArrayList<GoodsDTO> dataList;
    private ShoppingCartActivity mContext;
    private NumberFormat nf;
    private LayoutInflater mInflater;

    public GoodsAdapter(ArrayList<GoodsDTO> dataList, ShoppingCartActivity mContext) {
        this.dataList = dataList;
        this.mContext = mContext;
        // 对数字格式化，返回当前语言环境的格式1,000,000
        nf = NumberFormat.getCurrencyInstance();
        // 小数保留2位
        nf.setMaximumFractionDigits(2);
        mInflater = LayoutInflater.from(mContext);
    }

    // item顶部类型停留
    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = mInflater.inflate(R.layout.item_header_view, parent, false);
        }
        ((TextView)(convertView)).setText(dataList.get(position).getTbGoodsType().getTypeName());
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return dataList.get(position).getTbGoodsType().getTypeId();
    }

    @Override
    public int getCount() {
        if(dataList==null){
            return 0;
        }
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder holder = null;
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.item_goods,parent,false);
            holder = new ItemViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ItemViewHolder) convertView.getTag();
        }
        GoodsDTO item = dataList.get(position);
        holder.bindData(item);
        return convertView;
    }

    class ItemViewHolder implements View.OnClickListener{
        private TextView name,price,tvAdd,tvMinus,tvCount;
        private GoodsDTO item;
        private ImageView img;
//        private RatingBar ratingBar;

        public ItemViewHolder(View itemView) {
            name = itemView.findViewById(R.id.tvName);
            price = itemView.findViewById(R.id.tvPrice);
            tvCount = itemView.findViewById(R.id.count);
            tvMinus = itemView.findViewById(R.id.tvMinus);
            tvAdd = itemView.findViewById(R.id.tvAdd);
            img = itemView.findViewById(R.id.img);
//            ratingBar = itemView.findViewById(R.id.ratingBar);
            tvMinus.setOnClickListener(this);
            tvAdd.setOnClickListener(this);
        }

        public void bindData(GoodsDTO item){
            this.item = item;
            name.setText(item.getgName());
//            ratingBar.setRating(item.rating);
            item.setSelectNum(mContext.getSelectedItemCountById(item.getgId()));
            tvCount.setText(String.valueOf(item.getSelectNum()));
            price.setText(nf.format(item.getgPrice()));
            if (item.getgPhoto() != null && !"".equals(item.getgPhoto())) {
                Glide.with(mContext).load("http://101.200.166.167/GCSJProject/images/" + item.getgPhoto()).centerCrop().into(img);
            }
            if(item.getSelectNum()<1){
                tvCount.setVisibility(View.GONE);
                tvMinus.setVisibility(View.GONE);
            }else{
                tvCount.setVisibility(View.VISIBLE);
                tvMinus.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            ShoppingCartActivity activity = mContext;
            switch (v.getId()){
                case R.id.tvAdd: {
                    int count = activity.getSelectedItemCountById(item.getgId());
                    if (count < 1) {
                        tvMinus.setAnimation(getShowAnimation());
                        tvMinus.setVisibility(View.VISIBLE);
                        tvCount.setVisibility(View.VISIBLE);
                    }

                    if (count < item.getgNumber()) {
                        activity.add(item, false);
                        count++;
                        tvCount.setText(String.valueOf(count));
                        int[] loc = new int[2];
                        v.getLocationInWindow(loc);
                        activity.playAnimation(loc);
                    }
                }
                break;
                case R.id.tvMinus: {
                    int count = activity.getSelectedItemCountById(item.getgId());
                    if (count < 2) {
                        tvMinus.setAnimation(getHiddenAnimation());
                        tvMinus.setVisibility(View.GONE);
                        tvCount.setVisibility(View.GONE);
                    }
                    count--;
                    activity.remove(item, false);//activity.getSelectedItemCountById(item.id)
                    tvCount.setText(String.valueOf(count));

                }
                break;
                default:
                    break;
            }
        }
    }

    private Animation getShowAnimation(){
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0,720,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,2f
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0,1);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }

    private Animation getHiddenAnimation(){
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0,720,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,2f
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1,0);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }
}
