package com.frite.creativevues;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.frite.creativevues.db.DBProvider;
import com.frite.creativevues.fragment.NoPostsFragment;
import com.frite.creativevues.fragment.PostFragment;
import com.frite.creativevues.model.PostModel;

import com.google.firebase.firestore.Query;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private boolean isTransactionSafe = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBProvider firestore = DBProvider.getInstance();

        firestore.getDb()
                .collection("posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(this, "Error while loading posts", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (value == null) return;

                    if (value.isEmpty()) {
                        commitFragment(R.id.no_posts_fragment, new NoPostsFragment());
                    }

                    ArrayList<PostModel> result = new ArrayList<>();

                    value.getDocuments().forEach(document -> {
                        PostModel post = PostModel.createPost(document);
                        result.add(post);
                    });

                    commitFragment(R.id.post_fragment_container, new PostFragment(result, false));
                    Toast.makeText(this, "Posts loaded", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        isTransactionSafe = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isTransactionSafe = false;
    }

    private void commitFragment(int layout, Fragment fragment) {
        if (isTransactionSafe) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(layout, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}