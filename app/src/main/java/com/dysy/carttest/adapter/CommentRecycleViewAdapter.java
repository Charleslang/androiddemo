package com.dysy.carttest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dysy.carttest.R;

import java.util.List;

public class CommentRecycleViewAdapter extends RecyclerView.Adapter<CommentRecycleViewAdapter.CommentLayout> {
    private Context mContext;
    private List<String> commentList;

    public CommentRecycleViewAdapter(Context mContext, List<String> commentList) {
        this.mContext = mContext;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentLayout onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_comment_item, parent, false);
        return new CommentLayout(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentLayout holder, int position) {
        holder.commentDate.setText("2020-06-12");
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }


    class CommentLayout extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView userNickname, userComment, commentDate;

        public CommentLayout(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.user_img);
            userNickname = itemView.findViewById(R.id.user_nickname);
            userComment = itemView.findViewById(R.id.user_comment);
            commentDate = itemView.findViewById(R.id.comment_date);
        }
    }
}
