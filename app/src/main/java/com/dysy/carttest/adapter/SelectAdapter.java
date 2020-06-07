package com.dysy.carttest.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dysy.carttest.GoodsItem;
import com.dysy.carttest.R;
import com.dysy.carttest.ShoppingCartActivity;
import com.dysy.carttest.dto.GoodsDTO;

import java.text.NumberFormat;


public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder>{
    private ShoppingCartActivity activity;
//    private SparseArray<GoodsItem> dataList;
    private SparseArray<GoodsDTO> dataList;
    private NumberFormat nf;
    private LayoutInflater mInflater;
    public SelectAdapter(ShoppingCartActivity activity, SparseArray<GoodsDTO> dataList) {
        this.activity = activity;
        this.dataList = dataList;
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_selected_goods,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GoodsDTO item = dataList.valueAt(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        if(dataList==null) {
            return 0;
        }
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private GoodsDTO item;
        private TextView tvCost,tvCount,tvAdd,tvMinus,tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCost = itemView.findViewById(R.id.tvCost);
            tvCount = itemView.findViewById(R.id.count);
            tvMinus = itemView.findViewById(R.id.tvMinus);
            tvAdd = itemView.findViewById(R.id.tvAdd);
            tvMinus.setOnClickListener(this);
            tvAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tvAdd:
                    activity.add(item, true);
                    break;
                case R.id.tvMinus:
                    activity.remove(item, true);
                    break;
                default:
                    break;
            }
        }

        public void bindData(GoodsDTO item){
            this.item = item;
            tvName.setText(item.getgName());
            tvCost.setText(nf.format(item.getSelectNum()*item.getgPrice()));
            tvCount.setText(String.valueOf(item.getSelectNum()));
        }
    }
}
