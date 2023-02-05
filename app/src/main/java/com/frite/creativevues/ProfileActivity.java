package com.frite.creativevues;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.frite.creativevues.db.CustomFirebaseAuth;

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

        CustomFirebaseAuth firebaseUser = CustomFirebaseAuth.getInstance();

        if(firebaseUser != null)
        {
            title.setText(firebaseUser.getUser().getDisplayName());
        }
    }
}