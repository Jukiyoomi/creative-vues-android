package com.frite.creativevues;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.frite.creativevues.db.CustomFirebaseAuth;
import com.frite.creativevues.db.DBProvider;
import com.frite.creativevues.fragment.NoPostsFragment;
import com.frite.creativevues.fragment.PostFragment;
import com.frite.creativevues.model.PostModel;

import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";

    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logoutButton = (Button) findViewById(R.id.logout_btn);

        logoutButton.setOnClickListener(v -> {
            CustomFirebaseAuth.getInstance().logout();
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(i);
        });

        DBProvider firestore = DBProvider.getInstance();

        firestore.getDb()
                .collection("posts")
                .whereEqualTo("user", CustomFirebaseAuth.getInstance().getUser().getUid())
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(this, "Error while loading posts", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    if (value == null) return;

                    if (value.isEmpty()) {
                        transaction.replace(R.id.no_posts_fragment, new NoPostsFragment());
                        transaction.commit();
                        return;
                    }

                    ArrayList<PostModel> result = new ArrayList<>();

                    value.getDocuments().forEach(document -> {
                        PostModel post = PostModel.createPost(document);
                        result.add(post);
                    });

                    PostFragment postFragment = new PostFragment();
                    postFragment.setPosts(result);

                    transaction.replace(R.id.my_post_fragment_container, postFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    Toast.makeText(this, "Posts loaded", Toast.LENGTH_SHORT).show();
                });
    }
}