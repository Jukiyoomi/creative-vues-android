package com.frite.creativevues.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.frite.creativevues.R;

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