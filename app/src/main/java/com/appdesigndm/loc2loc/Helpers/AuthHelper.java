package com.appdesigndm.loc2loc.Helpers;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.appdesigndm.loc2loc.Models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

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

    public void setPhoto(final Uri uri) {
        UserProfileChangeRequest.Builder userBuilder = new UserProfileChangeRequest.Builder();
        mCurrentUser.updateProfile(userBuilder.setPhotoUri(uri).build())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // TODO: Select profile photo
                        // Update DDBB
                        String[] children = {DBHelper.USERS, mCurrentUser.getUid()};
                        DBHelper.setValue(children, uri);
                    }
                });
    }

    public void logout () {
        mAuth.signOut();
    }
}
