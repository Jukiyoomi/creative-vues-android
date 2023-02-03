package com.frite.creativevues;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.frite.creativevues.db.CustomFirebaseAuth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class AuthActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private CustomFirebaseAuth mAuth;
    SignInButton signInBtn;
    private static final String TAG = "AuthActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        signInBtn = findViewById(R.id.auth_btn);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
        ).requestIdToken("679842903143-tgkb52uard6oge3n9qfilfimpvgl9qak.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        signInBtn.setOnClickListener(view -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            activityResultLauncher.launch(signInIntent);
        });

        mAuth = CustomFirebaseAuth.getInstance();
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();

                    // Initialize task
                    Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn
                            .getSignedInAccountFromIntent(data);

                    if(signInAccountTask.isSuccessful())
                    {
                        Toast.makeText(AuthActivity.this, "Processing authentication", Toast.LENGTH_SHORT).show();

                        this.mAuth.initAuth(signInAccountTask, this, () ->
                                startActivity(new Intent(AuthActivity.this, ProfileActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
                    } else {
                        Log.d(TAG, "onActivityResult: " + signInAccountTask.getException());
                        Toast.makeText(AuthActivity.this, "Google sign in failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
}