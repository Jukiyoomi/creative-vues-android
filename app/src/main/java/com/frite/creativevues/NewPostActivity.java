package com.frite.creativevues;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

                if (s.length() > MAX_POST_LENGTH) {
                    counter.setTextColor(getResources().getColor(R.color.red, null));
                } else {
                    counter.setTextColor(getResources().getColor(R.color.blue, null));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setCounter(int count) {
        counter.setText(count + "/" + MAX_POST_LENGTH);
    }
}