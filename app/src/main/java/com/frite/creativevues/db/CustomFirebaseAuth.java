package com.frite.creativevues.db;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class CustomFirebaseAuth {
    private final FirebaseAuth mAuth;

    private static final CustomFirebaseAuth instance = new CustomFirebaseAuth();

    private CustomFirebaseAuth() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseUser getUser() {
        return this.mAuth.getCurrentUser();
    }

    public static CustomFirebaseAuth getInstance() {
        return instance;
    }

    public void initAuth(Task<GoogleSignInAccount> signInAccountTask, Context context, Runnable callback) {
        try {
            // Initialize login
            GoogleSignInAccount googleSignInAccount = signInAccountTask
                    .getResult(ApiException.class);

            if(googleSignInAccount != null) {
                // Initialize auth credential
                AuthCredential authCredential = GoogleAuthProvider
                        .getCredential(googleSignInAccount.getIdToken(),null);

                // Check credential
                this.checkCredential(authCredential, context, callback);
            }
        }
        catch (ApiException e)
        {
            e.printStackTrace();
        }
    }

    public void logout() {
        this.mAuth.signOut();
    }

    private void checkCredential(AuthCredential authCredential, Context context, Runnable callback) {
        this.mAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            Toast.makeText(context, "Firebase authentication successful", Toast.LENGTH_SHORT).show();
                            callback.run();
                        } else {
                            Toast.makeText(context, "Firebase authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    });
    }

    public boolean isLogged() {
        return this.mAuth.getCurrentUser() != null;
    }
}
