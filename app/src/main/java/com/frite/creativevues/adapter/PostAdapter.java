package com.frite.creativevues.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.frite.creativevues.R;
import com.frite.creativevues.model.PostModel;
import com.frite.creativevues.service.PostService;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    private final int layoutId;
    private final ArrayList<PostModel> posts;

    private boolean isEditMode;

    public PostAdapter(ArrayList<PostModel> posts, Context context, boolean isEditMode) {
        this.posts = posts;
        this.context = context;
        this.isEditMode = isEditMode;
        this.layoutId = isEditMode ? R.layout.item_post_edit : R.layout.item_post;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(this.layoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostModel currentPost = this.posts.get(position);

        Glide.with(this.context).load(currentPost.getAvatar()).into(holder.avatar);
        holder.text.setText(currentPost.getText());
        holder.username.setText(currentPost.getUsername());
        holder.date.setText(currentPost.getDate().toDate());

        if(this.isEditMode) {
            holder.trashBtn.setOnClickListener(v -> {
                Log.d("DELETE", "onBindViewHolder: " + currentPost.getText());
                PostService.getInstance(this.context).deletePost(currentPost.getId());
            });
        } else {
            if(holder.likes != null) {
                holder.likes.setText(String.valueOf(currentPost.getLikes().size()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.posts.size();
    }
}
