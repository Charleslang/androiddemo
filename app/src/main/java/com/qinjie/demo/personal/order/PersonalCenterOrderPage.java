package com.qinjie.demo.personal.order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.MyApplication;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dysy.carttest.R;
import com.qinjie.demo.utils.HttpClient1;
import com.qinjie.demo.utils.ThreadPoolExecutorService;

import java.util.List;


public class PersonalCenterOrderPage extends Fragment {

    public PersonalCenterOrderPage(Context context,Integer orderStaus) {
        // Required empty public constructor
        this.mContext = context;
        this.mOrderStatus = orderStaus;
    }

    /**
     * listview，订单列表
     */
    private ListView personal_center_order_page_list;
    private Context mContext;
    /**
     * 订单状态,1-已支付，0-未支付
     */
    private Integer mOrderStatus;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View re = inflater.inflate(R.layout.fragment_personal_center_order_page, container, false);
        personal_center_order_page_list = re.findViewById(R.id.personal_center_order_page_list);
        ThreadPoolExecutorService.add(new Runnable() {
            @Override
            public void run() {
                String res = HttpClient1.doGet(getString(R.string.server_path) + getString(R.string.interface_users_orders)+"?orderStatus="+mOrderStatus+"&pageNum=1&pageSize=100", ((MyApplication) getActivity().getApplication()).getToken());
                JSONObject jsonObject = JSONObject.parseObject(res);
                Message message = new Message();
                Bundle bundle = new Bundle();
                if(jsonObject.get("code").equals(200)){
                    bundle.putString("datas", jsonObject.get("datas") + "");
                    message.what = 0;
                }
                else{
                    bundle.putString("toast", "错误："+jsonObject.get("msg"));
                }
                message.setData(bundle);
                myHandler.sendMessage(message);
            }
        });

        return re;
    }
    @SuppressLint("HandlerLeak")
    private final Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: //获取订单信息成功
                    List<OrderInfo> mOrderInfoList = null;
                    mOrderInfoList = JSONArray.parseArray(""+msg.getData().get("datas"), OrderInfo.class);
                    if(mOrderInfoList!=null){
                        personal_center_order_page_list.setAdapter(new PersonalCenterOrderPageAdapter(mContext, mOrderInfoList));
                    }
                    break;
                default:
                    Toast.makeText(getContext(), "DEFAULT",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
