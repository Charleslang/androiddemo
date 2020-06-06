package com.dysy.carttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dysy.carttest.adapter.CommentRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private CommentRecycleViewAdapter adapter;
    private List<String> commentList = new ArrayList<>();

    private TextView commentBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initData();
        initView();


    }

    public void initView(){
        commentBack = findViewById(R.id.comment_back);

        mRecyclerView = findViewById(R.id.rv_comment_list);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        adapter = new CommentRecycleViewAdapter(this, commentList);
        mRecyclerView.setAdapter(adapter);

        commentBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initData(){
        commentList.add("123");
        commentList.add("dfg");
        commentList.add("555");
        commentList.add("666");
        commentList.add("777");
        commentList.add("888");
        commentList.add("999");
        commentList.add("1010");
        commentList.add("1111");
    }
}
