package com.dysy.carttest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dysy.carttest.adapter.GoodsAdapter;
import com.dysy.carttest.adapter.SelectAdapter;
import com.dysy.carttest.adapter.TypeAdapter;
import com.dysy.carttest.dto.GoodsDTO;
import com.dysy.carttest.dto.GoodsTypeDTO;
import com.dysy.carttest.entity.TbGoodsType;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener, Serializable{

    private ImageView imgCart;
    private ViewGroup anim_mask_layout;
    private RecyclerView rvType,rvSelected;
    private TextView tvCount,tvCost,tvSubmit,tvTips, shoppingTopContent;
    private BottomSheetLayout bottomSheetLayout;
    private View bottomSheet;
    private StickyListHeadersListView listView;

//    private ArrayList<GoodsItem> dataList,typeList;
    private ArrayList<GoodsDTO> dataList = new ArrayList<>();
    private ArrayList<GoodsTypeDTO> typeList = new ArrayList<>();
//    private SparseArray<GoodsItem> selectedList;
    private SparseArray<GoodsDTO> selectedList;
    private SparseIntArray groupSelect;

    private GoodsAdapter myAdapter;
    private SelectAdapter selectAdapter;
    private TypeAdapter typeAdapter;

    private NumberFormat nf;
    private Handler mHanlder;
    private TextView shoppingBackBtn, shoppingCommentBtn;

    private OkHttpClient okHttpClient = new OkHttpClient();
    private GoodsThread goodsThread;
    private double cost = 0;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    initView();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        mHanlder = new Handler(getMainLooper());
//        dataList = GoodsItem.getGoodsList();
//        typeList = GoodsItem.getTypeList();
        selectedList = new SparseArray<>();
        groupSelect = new SparseIntArray();
//        goodsThread = new GoodsThread();
//        goodsThread.start();
//        try {
//            goodsThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        getAllGoods();

//        initView();
    }

    private void initView(){
        tvCount = findViewById(R.id.tvCount);
        tvCost = findViewById(R.id.tvCost);
        tvTips = findViewById(R.id.tvTips);
        tvSubmit  = findViewById(R.id.tvSubmit);
        rvType = findViewById(R.id.typeRecyclerView);
        shoppingBackBtn = findViewById(R.id.shopping_back_btn);
        shoppingCommentBtn = findViewById(R.id.shopping_comment_btn);
        shoppingTopContent = findViewById(R.id.shopping_topcontent);

        shoppingTopContent.getPaint().setFakeBoldText(true);

        imgCart = findViewById(R.id.imgCart);
        anim_mask_layout = (RelativeLayout) findViewById(R.id.containerLayout);
        bottomSheetLayout = findViewById(R.id.bottomSheetLayout);

        listView = findViewById(R.id.itemListView);

        rvType.setLayoutManager(new LinearLayoutManager(this));
        typeAdapter = new TypeAdapter(this,typeList);
        rvType.setAdapter(typeAdapter);
        rvType.addItemDecoration(new DividerDecoration(this));

        myAdapter = new GoodsAdapter(dataList,this);
        listView.setAdapter(myAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                GoodsDTO item = dataList.get(firstVisibleItem);
                if(typeAdapter.selectTypeId != item.getTbGoodsType().getTypeId()) {
                    typeAdapter.selectTypeId = item.getTbGoodsType().getTypeId();
                    typeAdapter.notifyDataSetChanged();
                    rvType.smoothScrollToPosition(getSelectedGroupPosition(item.getTbGoodsType().getTypeId()));
                }
            }
        });

        shoppingBackBtn.setOnClickListener(this);
        shoppingCommentBtn.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);

    }


    public void playAnimation(int[] start_location){
        ImageView img = new ImageView(this);
        img.setImageResource(R.drawable.button_add);
        setAnim(img,start_location);
    }

    private Animation createAnim(int startX,int startY){
        int[] des = new int[2];
        imgCart.getLocationInWindow(des);

        AnimationSet set = new AnimationSet(false);

        Animation translationX = new TranslateAnimation(0, des[0]-startX, 0, 0);
        translationX.setInterpolator(new LinearInterpolator());
        Animation translationY = new TranslateAnimation(0, 0, 0, des[1]-startY);
        translationY.setInterpolator(new AccelerateInterpolator());
        Animation alpha = new AlphaAnimation(1,0.5f);
        set.addAnimation(translationX);
        set.addAnimation(translationY);
        set.addAnimation(alpha);
        set.setDuration(500);

        return set;
    }

    private void addViewToAnimLayout(final ViewGroup vg, final View view,
                                     int[] location) {

        int x = location[0];
        int y = location[1];
        int[] loc = new int[2];
        vg.getLocationInWindow(loc);
        view.setX(x);
        view.setY(y-loc[1]);
        vg.addView(view);
    }
    private void setAnim(final View v, int[] start_location) {

        addViewToAnimLayout(anim_mask_layout, v, start_location);
        Animation set = createAnim(start_location[0],start_location[1]);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                mHanlder.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        anim_mask_layout.removeView(v);
                    }
                },100);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(set);
    }

    // 重写onClick
    @Override
    public void onClick(View v){
        Intent intent = null;
        switch (v.getId()){
            case R.id.bottom:
                showBottomSheet();
                break;
            case R.id.clear:
                clearCart();
                break;
            case R.id.tvSubmit:
                Toast.makeText(ShoppingCartActivity.this, "结算", Toast.LENGTH_SHORT).show();
                intent = new Intent(ShoppingCartActivity.this, OrderActivity.class);
                Map<String, Object> map = new HashMap<>();
                map.put("goodsList", selectedList);
                map.put("cost", cost);
                intent.putExtra("map",(Serializable) map);
                startActivity(intent);
                break;
            case R.id.shopping_back_btn:
                Toast.makeText(ShoppingCartActivity.this, "返回", Toast.LENGTH_SHORT).show();
                break;
            case R.id.shopping_comment_btn:
                intent = new Intent(ShoppingCartActivity.this, CommentActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    //添加商品
    public void add(GoodsDTO item,boolean refreshGoodList){

        int groupCount = groupSelect.get(item.getTbGoodsType().getTypeId());
        if(groupCount==0){
            groupSelect.append(item.getTbGoodsType().getTypeId(),1);
        }else{
            groupSelect.append(item.getTbGoodsType().getTypeId(),++groupCount);
        }

        GoodsDTO temp = selectedList.get(item.getgId());
        if(temp==null){
            item.setSelectNum(1);
            selectedList.append(item.getgId(),item);
        }else{
            temp.setSelectNum(temp.getSelectNum() + 1);
        }
        update(refreshGoodList);
    }

    //移除商品
    public void remove(GoodsDTO item,boolean refreshGoodList){

        int groupCount = groupSelect.get(item.getTbGoodsType().getTypeId());
        if(groupCount==1){
            groupSelect.delete(item.getTbGoodsType().getTypeId());
        }else if(groupCount>1){
            groupSelect.append(item.getTbGoodsType().getTypeId(),--groupCount);
        }

        GoodsDTO temp = selectedList.get(item.getgId());
        if(temp!=null){
            if(temp.getSelectNum()<2){
                selectedList.remove(item.getgId());
            }else{
                item.setSelectNum(item.getSelectNum() - 1);
            }
        }
        update(refreshGoodList);
    }

    //刷新布局 总价、购买数量等
    private void update(boolean refreshGoodList){
        int size = selectedList.size();
        int count =0;
//        double cost = 0;
        cost = 0;
        for(int i=0;i<size;i++){
            GoodsDTO item = selectedList.valueAt(i);
            count += item.getSelectNum();
            cost += item.getSelectNum()*item.getgPrice();
        }

        if(count<1){
            tvCount.setVisibility(View.GONE);
        }else{
            tvCount.setVisibility(View.VISIBLE);
        }

        tvCount.setText(String.valueOf(count));

        if(cost > 10){
            tvTips.setVisibility(View.GONE);
            tvSubmit.setVisibility(View.VISIBLE);
        }else{
            tvSubmit.setVisibility(View.GONE);
            tvTips.setVisibility(View.VISIBLE);
        }

        tvCost.setText(nf.format(cost));

        if(myAdapter!=null && refreshGoodList){
            myAdapter.notifyDataSetChanged();
        }
        if(selectAdapter!=null){
            selectAdapter.notifyDataSetChanged();
        }
        if(typeAdapter!=null){
            typeAdapter.notifyDataSetChanged();
        }
        if(bottomSheetLayout.isSheetShowing() && selectedList.size()<1){
            bottomSheetLayout.dismissSheet();
        }
    }

    //清空购物车
    public void clearCart(){
        selectedList.clear();
        groupSelect.clear();
        update(true);

    }

    //根据商品id获取当前商品的采购数量
    public int getSelectedItemCountById(int id){
        GoodsDTO temp = selectedList.get(id);
        if(temp==null){
            return 0;
        }
        return temp.getSelectNum();
    }

    //根据类别Id获取属于当前类别的数量
    public int getSelectedGroupCountByTypeId(int typeId){
        return groupSelect.get(typeId);
    }
    //根据类别id获取分类的Position 用于滚动左侧的类别列表
    public int getSelectedGroupPosition(int typeId){
        for(int i=0;i<typeList.size();i++){
            if(typeId==typeList.get(i).getTypeId()){
                return i;
            }
        }
        return 0;
    }

    public void onTypeClicked(int typeId){
        listView.setSelection(getSelectedPosition(typeId));
    }

    private int getSelectedPosition(int typeId){
        int position = 0;
        for(int i=0;i<dataList.size();i++){
            if(dataList.get(i).getTbGoodsType().getTypeId() == typeId){
                position = i;
                break;
            }
        }
        return position;
    }

    private View createBottomSheetView(){
        View view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet,(ViewGroup) getWindow().getDecorView(),false);
        rvSelected = view.findViewById(R.id.selectRecyclerView);
        rvSelected.setLayoutManager(new LinearLayoutManager(this));
        TextView clear = view.findViewById(R.id.clear);
        clear.setOnClickListener(this);
        selectAdapter = new SelectAdapter(this,selectedList);
        rvSelected.setAdapter(selectAdapter);
        return view;
    }

    private void showBottomSheet(){
        if(bottomSheet==null){
            bottomSheet = createBottomSheetView();
        }
        if(bottomSheetLayout.isSheetShowing()){
            bottomSheetLayout.dismissSheet();
        }else {
            if(selectedList.size()!=0){
                bottomSheetLayout.showWithSheetView(bottomSheet);
            }
        }
    }

    class GoodsThread extends Thread{
        @Override
        public void run() {
            Request request = new Request.Builder()
                    .url("http://192.168.43.131:8080/GCSJProject/goods/goods")
                    .get()
                    .build();
            try (Response response = okHttpClient.newCall(request).execute()){
                Gson gson = new Gson();
                List<TbGoodsType> list = gson.fromJson(response.body().string().toString(), new TypeToken<List<TbGoodsType>>() {
                }.getType());
                if (!list.isEmpty()) {
                    for (TbGoodsType tbGoodsType : list) {
                        GoodsTypeDTO goodsTypeDTO = new GoodsTypeDTO(tbGoodsType.getTypeId(), tbGoodsType.getTypeName());
                        for (GoodsDTO goodsDTO : tbGoodsType.getTbGoodsList()) {
                            GoodsDTO goods = new GoodsDTO(goodsDTO.getgId(), goodsDTO.getgName(), goodsDTO.getgPrice(),
                                    goodsDTO.getSelectNum(), goodsDTO.getgPhoto(), goodsDTO.getTbGoodsType());
                            dataList.add(goods);
                        }
                        typeList.add(goodsTypeDTO);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void getAllGoods(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url("http://192.168.43.131:8080/GCSJProject/goods/goods")
                        .get()
                        .build();
                try (Response response = okHttpClient.newCall(request).execute()){
                    Gson gson = new Gson();
                    List<TbGoodsType> list = gson.fromJson(response.body().string().toString(), new TypeToken<List<TbGoodsType>>() {
                    }.getType());
                    if (!list.isEmpty()) {
                        for (TbGoodsType tbGoodsType : list) {
                            GoodsTypeDTO goodsTypeDTO = new GoodsTypeDTO(tbGoodsType.getTypeId(), tbGoodsType.getTypeName());
                            for (GoodsDTO goodsDTO : tbGoodsType.getTbGoodsList()) {
                                GoodsDTO goods = new GoodsDTO(goodsDTO.getgId(), goodsDTO.getgName(), goodsDTO.getgPrice(),
                                        goodsDTO.getSelectNum(), goodsDTO.getgPhoto(), goodsDTO.getTbGoodsType());
                                dataList.add(goods);
                            }
                            typeList.add(goodsTypeDTO);
                        }
                        Message message = new Message();
                        message.what = 1;
                        mHandler.sendMessage(message);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
