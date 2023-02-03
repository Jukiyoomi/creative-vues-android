package com.frite.creativevues;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.frite.creativevues.db.DBProvider;
import com.frite.creativevues.fragment.PostFragment;
import com.frite.creativevues.model.PostModel;
import com.frite.creativevues.model.TimestampModel;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBProvider firestore = DBProvider.getInstance();

        firestore.getDb()
                .collection("posts")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Toast.makeText(this, "Error while loading posts", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    ArrayList<PostModel> result = new ArrayList<>();

                    if (value != null) {
                        value.getDocuments().forEach(document -> {
                            PostModel post = createPost(document);
                            result.add(post);
                        });
                    }

                    PostFragment postFragment = new PostFragment();
                    postFragment.setPosts(result);

                    transaction.replace(R.id.post_fragment_container, postFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    Toast.makeText(this, "Posts loaded", Toast.LENGTH_SHORT).show();
                });
    }

    private PostModel createPost(DocumentSnapshot document) {
        PostModel post = new PostModel(document.getId());
        post.setText(Objects.requireNonNull(document.get("text")).toString());
        post.setAvatar(Objects.requireNonNull(document.get("avatar")).toString());
        post.setUsername(Objects.requireNonNull(document.get("username")).toString());
        post.setModified(Objects.requireNonNull((Boolean) document.get("modified")));
        post.setUser(Objects.requireNonNull(document.get("user")).toString());

        Timestamp postTimestamp = (Timestamp) Objects.requireNonNull(document.get("timestamp"));

        post.setDate(new TimestampModel(postTimestamp));

        // Convert likes into string, then split it and remove square brackets
        List<String> likes = Arrays.asList(
                Objects.requireNonNull(document.get("likes"))
                        .toString()
                        .replace("[", "")
                        .replace("]", "")
                        .split(", ")
        );
        post.setLikes(likes);
        return post;
    }
}