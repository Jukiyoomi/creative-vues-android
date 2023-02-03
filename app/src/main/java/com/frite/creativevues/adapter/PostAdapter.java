package com.frite.creativevues.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.frite.creativevues.R;
import com.frite.creativevues.model.PostModel;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context context;
    private final int layoutId;
    private final ArrayList<PostModel> posts;

        class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView avatar;
            public TextView text;
            public TextView username;
            public TextView likes;
            public TextView date;

            public ViewHolder(View view) {
                super(view);
                this.avatar = view.findViewById(R.id.post_avatar);
                this.text = view.findViewById(R.id.post_text);
                this.username = view.findViewById(R.id.post_username);
                this.likes = view.findViewById(R.id.post_likes);
                this.date = view.findViewById(R.id.post_date);
            }
        }

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
        holder.likes.setText(String.valueOf(currentPost.getLikes().size()));

    }

    @Override
    public int getItemCount() {
        return this.posts.size();
    }
}
