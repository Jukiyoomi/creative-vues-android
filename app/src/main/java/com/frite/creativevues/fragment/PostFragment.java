package com.frite.creativevues.fragment;

import android.os.Bundle;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<PostModel> posts;
    private String mParam1;
    private String mParam2;

    public PostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment post_fragment.
     */
    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        ArrayList<PostModel> posts = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            posts.add(new PostModel("This is a test post" + i));
        }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.all_posts);
        recyclerView.setAdapter(new PostAdapter(R.layout.item_post, this.posts, getContext()));

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        return view;
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