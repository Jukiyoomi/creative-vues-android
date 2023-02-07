package com.frite.creativevues.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.frite.creativevues.R;
import com.frite.creativevues.adapter.PostAdapter;
import com.frite.creativevues.model.PostModel;

import java.util.ArrayList;

public class PostFragment extends Fragment {
    private ArrayList<PostModel> posts;

    private boolean onEditMode;

    ImageView trash_btn;

    public PostFragment() {
        // Required empty public constructor
    }

    public PostFragment(ArrayList<PostModel> posts, boolean onEditMode) {
        this.posts = posts;
        this.onEditMode = onEditMode;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.all_posts);

        int layout = onEditMode ? R.layout.item_post_edit : R.layout.item_post;

        recyclerView.setAdapter(new PostAdapter(layout, this.posts, getContext()));

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        if(this.onEditMode) {
            trash_btn = view.findViewById(R.id.btn_delete);
        }
    }

    public ArrayList<PostModel> getPosts() {
        return this.posts;
    }

    public void setPosts(ArrayList<PostModel> posts) {
        this.posts = posts;
    }

    public void addPost(PostModel post) {
        if(!this.posts.contains(post)) {
            this.posts.add(post);
        }
    }
    public void removePost(PostModel post) {
        this.posts.remove(post);
    }
}