package com.frite.creativevues.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frite.creativevues.AuthActivity;
import com.frite.creativevues.MainActivity;
import com.frite.creativevues.NewPostActivity;
import com.frite.creativevues.ProfileActivity;
import com.frite.creativevues.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HeaderFragment extends Fragment {

    TextView title;
    Button joinBtn;
    Button postBtn;
    ImageView avatar;
    FirebaseAuth mAuth;

    public HeaderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_header, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        title = view.findViewById(R.id.toolbar_title);
        joinBtn = view.findViewById(R.id.join_btn);
        postBtn = view.findViewById(R.id.post_btn);
        avatar = view.findViewById(R.id.profile_btn);

        title.setOnClickListener(mainView -> {
            Intent i = new Intent(getActivity(), MainActivity.class);
            startActivity(i);
        });

        joinBtn.setOnClickListener(authView -> {
            Intent i = new Intent(getActivity(), AuthActivity.class);
            startActivity(i);
        });

        postBtn.setOnClickListener(postView -> {
            Intent i = new Intent(getActivity(), NewPostActivity.class);
            startActivity(i);
        });

        avatar.setOnClickListener(profileView -> {
            Intent i = new Intent(getActivity(), ProfileActivity.class);
            startActivity(i);
        });

        mAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        // Check condition
        if(firebaseUser != null) {
            Glide.with(this).load(firebaseUser.getPhotoUrl()).into(avatar);

            joinBtn.setVisibility(View.INVISIBLE);
            postBtn.setVisibility(View.VISIBLE);
            avatar.setVisibility(View.VISIBLE);
        }
    }

}