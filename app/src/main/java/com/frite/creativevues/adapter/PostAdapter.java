package com.frite.creativevues.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.frite.creativevues.model.PostModel;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    private final int layoutId;
    private final ArrayList<PostModel> posts;



    public PostAdapter(int layoutId, ArrayList<PostModel> posts, Context context) {
        this.layoutId = layoutId;
        this.posts = posts;
        this.context = context;
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

        if(holder.likes != null) {
            holder.likes.setText(String.valueOf(currentPost.getLikes().size()));
        }
    }

    @Override
    public int getItemCount() {
        return this.posts.size();
    }
}
