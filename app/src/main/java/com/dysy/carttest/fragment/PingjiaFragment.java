package com.dysy.carttest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.dysy.carttest.R;
import com.dysy.carttest.adapter.CommentRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class PingjiaFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private CommentRecycleViewAdapter adapter;
    private List<String> commentList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rv_comment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        mRecyclerView = view.findViewById(R.id.rv_comment_list);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        adapter = new CommentRecycleViewAdapter(getActivity(), commentList);
        mRecyclerView.setAdapter(adapter);
    }

    public void initData(){
        commentList.add("123");
        commentList.add("dfg");
        commentList.add("555");
        commentList.add("666");
        commentList.add("777");
        commentList.add("888");
    }
}
