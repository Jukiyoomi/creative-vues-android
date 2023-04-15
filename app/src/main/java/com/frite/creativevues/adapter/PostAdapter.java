package com.frite.creativevues.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.frite.creativevues.R;
import com.frite.creativevues.db.CustomFirebaseAuth;
import com.frite.creativevues.model.PostModel;
import com.frite.creativevues.service.PostService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

public class PostAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context context;
    private final int layoutId;
    private final ArrayList<PostModel> posts;

    private final boolean isEditMode;

    CustomFirebaseAuth mAuth = CustomFirebaseAuth.getInstance();

    String userId = mAuth.isLogged() ? mAuth.getUser().getUid() : "";

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

        boolean isLiked = PostService.getInstance(this.context).isLiked(userId, currentPost.getLikes());

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
                this.updateLikeBtn(holder.likeBtn, isLiked);
                holder.likeBtn.setOnClickListener(v -> {
                    PostService.getInstance(this.context).likePost(currentPost.getId(), userId, currentPost.getLikes());
//                    this.updateLikeBtn(holder.likeBtn, isLiked);
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.posts.size();
    }

    private void updateLikeBtn(ImageView btn, boolean isLiked) {
        if (isLiked) {
            btn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.like_liked));
        } else {
            btn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.like_unliked));
        }
    }
}
