package com.frite.creativevues;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HeaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HeaderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView title;
    Button joinBtn;
    Button postBtn;
    ImageView avatar;
    FirebaseAuth mAuth;

    public HeaderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HeaderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HeaderFragment newInstance(String param1, String param2) {
        HeaderFragment fragment = new HeaderFragment();
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