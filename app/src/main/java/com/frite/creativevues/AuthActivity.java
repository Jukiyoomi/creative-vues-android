package com.frite.creativevues;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
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

        mAuth = FirebaseAuth.getInstance();
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

                        try {
                            // Initialize sign in account
                            GoogleSignInAccount googleSignInAccount = signInAccountTask
                                    .getResult(ApiException.class);

                            if(googleSignInAccount != null) {
                                // Initialize auth credential
                                AuthCredential authCredential = GoogleAuthProvider
                                        .getCredential(googleSignInAccount.getIdToken(),null);

                                // Check credential
                                checkCredential(authCredential);
                            }
                        }
                        catch (ApiException e)
                        {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d(TAG, "onActivityResult: " + signInAccountTask.getException());
                        Toast.makeText(AuthActivity.this, "Google sign in failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void checkCredential(AuthCredential authCredential) {
        mAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(AuthActivity.this, "Firebase authentication successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AuthActivity.this, ProfileActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    } else {
                        Toast.makeText(AuthActivity.this, "Firebase authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}