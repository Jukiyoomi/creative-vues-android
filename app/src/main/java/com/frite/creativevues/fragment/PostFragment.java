package com.frite.creativevues.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frite.creativevues.R;
import com.frite.creativevues.adapter.PostAdapter;
import com.frite.creativevues.model.PostModel;

import java.util.ArrayList;

public class PostFragment extends Fragment {
    private ArrayList<PostModel> posts;

    private boolean onEditMode;

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

        recyclerView.setAdapter(new PostAdapter(this.posts, getContext(), this.onEditMode));

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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