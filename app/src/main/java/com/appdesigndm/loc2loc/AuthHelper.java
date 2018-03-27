package com.appdesigndm.loc2loc;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthHelper {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    public AuthHelper() {
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public FirebaseUser getCurrentUser() {
        return mCurrentUser;
    }

    public String getName() {
        return mCurrentUser.getDisplayName();
    }

    public String getEmail() {
        return mCurrentUser.getEmail();
    }

    public String getId() {
        return mCurrentUser.getUid();
    }

    public Uri getPhoto() {
        return mCurrentUser.getPhotoUrl();
    }

    public void logout () {
        mAuth.signOut();
    }
}
