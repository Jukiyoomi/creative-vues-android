package com.frite.creativevues;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.frite.creativevues.db.CustomFirebaseAuth;
import com.frite.creativevues.db.DBProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class NewPostActivity extends AppCompatActivity {
    private static final String TAG = "NewPostActivity";
    private static final int MAX_POST_LENGTH = 300;
    private TextView counter;
    private EditText postText;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        counter = findViewById(R.id.post_count);
        postText = findViewById(R.id.post_textarea);
        submitBtn = findViewById(R.id.submit_post_btn);

        setCounter(postText.getText().length());

        postText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setCounter(s.length());

                if (isPostTooLong()) {
                    counter.setTextColor(getResources().getColor(R.color.red, null));
                } else {
                    counter.setTextColor(getResources().getColor(R.color.blue, null));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        submitBtn.setOnClickListener(v -> {
            if (isPostEmpty() || isPostTooLong()) {
                postText.setError("Post can neither be empty nor longer than " + MAX_POST_LENGTH + " characters");
            } else {
                HashMap<String, Object> data = new HashMap<>();

                data.put("text", postText.getText().toString().trim());
                data.put("user", CustomFirebaseAuth.getInstance().getUser().getUid());
                data.put("avatar", Objects.requireNonNull(CustomFirebaseAuth.getInstance().getUser().getPhotoUrl()).toString());
                data.put("username", CustomFirebaseAuth.getInstance().getUser().getDisplayName());
                data.put("modified", false);
                data.put("likes", new ArrayList<>());
                data.put("timestamp", DBProvider.getInstance().getTimestamp());

                try {
                    DBProvider.getInstance().getDb()
                            .collection("posts")
                            .add(data)
                            .addOnSuccessListener(documentReference -> {
                                Log.d(TAG, "Post written successfully !");
                                Toast.makeText(NewPostActivity.this, "Post written successfully !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NewPostActivity.this, MainActivity.class);
                                startActivity(intent);
                            })
                            .addOnFailureListener(e -> {
                                Log.d(TAG, "Error adding document", e);
                                Toast.makeText(NewPostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setCounter(int count) {
        counter.setText(count + "/" + MAX_POST_LENGTH);
    }

    private boolean isPostTooLong() {
        return postText.getText().length() >= MAX_POST_LENGTH;
    }

    private boolean isPostEmpty() {
        return postText.getText().length() == 0;
    }
}