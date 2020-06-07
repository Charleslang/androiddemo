package com.dysy.carttest.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dysy.carttest.GoodsItem;
import com.dysy.carttest.R;
import com.dysy.carttest.ShoppingCartActivity;
import com.dysy.carttest.dto.GoodsDTO;
import com.dysy.carttest.dto.GoodsTypeDTO;

import java.util.ArrayList;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {
    public int selectTypeId;
    public ShoppingCartActivity activity;
//    public ArrayList<GoodsItem> dataList;
    public ArrayList<GoodsTypeDTO> dataList;

    public TypeAdapter(ShoppingCartActivity activity, ArrayList<GoodsTypeDTO> dataList) {
        this.activity = activity;
        this.dataList = dataList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GoodsTypeDTO item = dataList.get(position);

        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        if(dataList==null){
            return 0;
        }
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvCount,type;
        private GoodsTypeDTO item;
        public ViewHolder(View itemView) {
            super(itemView);
            tvCount = itemView.findViewById(R.id.tvCount);
            type = itemView.findViewById(R.id.type);
            itemView.setOnClickListener(this);
        }

        public void bindData(GoodsTypeDTO item){
            this.item = item;
            type.setText(item.getTypeName());
            int count = activity.getSelectedGroupCountByTypeId(item.getTypeId());
            tvCount.setText(String.valueOf(count));
            if(count<1){
                tvCount.setVisibility(View.GONE);
            }else{
                tvCount.setVisibility(View.VISIBLE);
            }
            if(item.getTypeId()==selectTypeId){
                itemView.setBackgroundColor(Color.WHITE);
            }else{
                itemView.setBackgroundColor(Color.TRANSPARENT);
            }

        }

        @Override
        public void onClick(View v) {
            activity.onTypeClicked(item.getTypeId());
        }
    }
}
