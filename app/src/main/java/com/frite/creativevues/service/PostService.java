package com.frite.creativevues.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.frite.creativevues.db.DBProvider;
import java.util.HashMap;

public class PostService {
    private static PostService instance;

    private static final String TAG = "PostService";

    private Context context;

    private PostService(Context ctx) {
        this.context = ctx;
    }

    public static PostService getInstance(Context ctx) {
       if (instance == null) {
          instance = new PostService(ctx);
       }
        return instance;
    }

    public void createPost(HashMap<String, Object> newPost, Runnable callback) {
        try {
            DBProvider.getInstance().getDb().collection("posts").add(newPost)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(context, "Post written successfully !", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Post written successfully !");
                        callback.run();
                    })
                    .addOnFailureListener(e -> {
                        Log.d(TAG, "Error adding document", e);
                        Toast.makeText(context, "Error creating new post, please try again", Toast.LENGTH_SHORT).show();
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePost(String id) {
        DBProvider.getInstance().getDb().collection("posts").document(id).delete();
    }
}
