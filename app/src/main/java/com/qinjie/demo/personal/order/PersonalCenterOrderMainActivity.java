package com.qinjie.demo.personal.order;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.dysy.carttest.R;
import com.library.tabstrip.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;
/**
  * 个人中心订单主页面
  *
  * @author:
 **/
public class PersonalCenterOrderMainActivity extends AppCompatActivity {
    /**
     * ViewPage
     */
    private ViewPager personal_center_order_fragment;
    /**
     * 返回的图标
     */
    private ImageView personal_center_order_return;
    /**
     * 上部滑动
     */
    private PagerSlidingTabStrip personal_center_order_status;
    /**
     * 刷新
     */
    private SwipeRefreshLayout personal_center_order_swipeRefreshLayout;

    /**
     * 保存fragment的list
     */
    private List<Fragment> fragmentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center_order_main);

        //得到ViewPager
        personal_center_order_fragment = findViewById(R.id.personal_center_order_fragment);
        personal_center_order_return = findViewById(R.id.personal_center_order_return);
        //返回按钮
        personal_center_order_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalCenterOrderMainActivity.this.finish();
            }
        });
        personal_center_order_status = findViewById(R.id.personal_center_order_status);
        //设置fragment的list
        fragmentList.add(new PersonalCenterOrderPage(PersonalCenterOrderMainActivity.this, -1));
        fragmentList.add(new PersonalCenterOrderPage(PersonalCenterOrderMainActivity.this, 1));
        fragmentList.add(new PersonalCenterOrderPage(PersonalCenterOrderMainActivity.this, 0));
        //显示的订单标题
        String titles[] = new String[3];
        titles[0] = ("全部");
        titles[1] = ("已支付");
        titles[2] = ("未支付");
        //设置适配器
        personal_center_order_fragment.setAdapter(new TextAdapter(getSupportFragmentManager(), titles, fragmentList));

        // 设置Tab底部选中的指示器 Indicator的颜色
        personal_center_order_status.setIndicatorColor(Color.BLACK);
        //设置Tab标题文字的颜色
        personal_center_order_status.setTextColor(Color.BLACK);
        // 设置Tab标题文字的大小
        personal_center_order_status.setTextSize(40);
        //设置Tab底部分割线的颜色
        personal_center_order_status.setUnderlineColor(Color.TRANSPARENT);
        // 设置点击某个Tab时的背景色,设置为0时取消背景色
        personal_center_order_status.setTabBackground(0);
        // 设置Tab是自动填充满屏幕的
        personal_center_order_status.setShouldExpand(true);
        //!!!设置选中的Tab文字的颜色!!!
        personal_center_order_status.setSelectedTextColor(Color.BLACK);
        //tab间的分割线
        personal_center_order_status.setDividerColor(Color.GRAY);
        //底部横线与字体宽度一致
        personal_center_order_status.setIndicatorinFollower(true);
        //与ViewPager关联，这样指示器就可以和ViewPager联动
        personal_center_order_status.setViewPager(personal_center_order_fragment);


        personal_center_order_swipeRefreshLayout = findViewById(R.id.personal_center_order_swipeRefreshLayout);
        //设置上拉刷新
        personal_center_order_swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);

        personal_center_order_swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        personal_center_order_swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(PersonalCenterOrderMainActivity.this, "上拉刷新完成", Toast.LENGTH_SHORT).show();
                        //设置fragment的list
                        fragmentList.add(new PersonalCenterOrderPage(PersonalCenterOrderMainActivity.this, -1));
                        fragmentList.add(new PersonalCenterOrderPage(PersonalCenterOrderMainActivity.this, 1));
                        fragmentList.add(new PersonalCenterOrderPage(PersonalCenterOrderMainActivity.this, 0));
                        //显示的订单标题
                        String titles[] = new String[3];
                        titles[0] = ("全部");
                        titles[1] = ("已支付");
                        titles[2] = ("未支付");

                        //设置适配器
                        personal_center_order_fragment.setAdapter(new TextAdapter(getSupportFragmentManager(), titles, fragmentList));
                    }
                }, 2000);
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
