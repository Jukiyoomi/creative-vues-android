package com.frite.creativevues;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.frite.creativevues.db.CustomFirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        title = findViewById(R.id.profile_title);

        CustomFirebaseAuth firebaseUser = CustomFirebaseAuth.getInstance();

        if(firebaseUser != null)
        {
            title.setText(firebaseUser.getUser().getDisplayName());
        }
    }
}