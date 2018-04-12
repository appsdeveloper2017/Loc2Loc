package com.appdesigndm.loc2loc.Helpers;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.appdesigndm.loc2loc.Callbacks.OnFinish;
import com.appdesigndm.loc2loc.Components.EditPasswordDialog;
import com.appdesigndm.loc2loc.LocApplication;
import com.appdesigndm.loc2loc.Models.UserModel;
import com.appdesigndm.loc2loc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class AuthHelper {

    public static final String NAME = "Name";
    public static final String EMAIL = "Email";
    public static final String PASSWORD = "Password";
    public static final String PHOTO = "Photo";

    private FirebaseAuth mAuth;
    private FirebaseUser mAuthenticatedUser;
    private Context mContext;

    public AuthHelper(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mAuthenticatedUser = mAuth.getCurrentUser();
        mContext = context;
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public FirebaseUser getAuthenticatedUser() {
        return mAuthenticatedUser;
    }

    public String getName() {
        return mAuthenticatedUser.getDisplayName();
    }

    public String getEmail() {
        return mAuthenticatedUser.getEmail();
    }

    public String getId() {
        return mAuthenticatedUser.getUid();
    }

    public Uri getPhoto() {
        return mAuthenticatedUser.getPhotoUrl();
    }

    public UserModel getUser() {
        UserModel user = new UserModel();

        user.setId(mAuthenticatedUser.getUid());
        user.setName(mAuthenticatedUser.getDisplayName());
        user.setEmail(mAuthenticatedUser.getEmail());
        user.setPhoto(mAuthenticatedUser.getPhotoUrl());

        return user;
    }

    public void updateProfile(UserModel user, String field) {
        switch (field) {
            case NAME:
                setName(user);
                break;
            case EMAIL:
                setEmail(user);
                break;
            case PHOTO:
                setPhoto(user);
                break;
        }
    }

    public void updatePassword(String oldPassword, final String newPassword, final EditPasswordDialog dialog) {
        final UserModel user = getUser();
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);

        mAuthenticatedUser.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mAuthenticatedUser.updatePassword(newPassword)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                updateUserInDB(user);
                                                dialog.getOnFinish().run(OnFinish.OnFinishResultCode.SUCCES);
                                            } else {
                                                LocApplication.printShort(mContext, R.string.error_updating_profile);
                                                dialog.getOnFinish().run(OnFinish.OnFinishResultCode.FAILURE);
                                            }
                                        }
                                    });
                        } else {
                            LocApplication.printShort(mContext, R.string.error_invalid_password);
                        }
                    }
                });
    }

    private void setName(final UserModel user) {
        UserProfileChangeRequest.Builder userBuilder = new UserProfileChangeRequest.Builder();

        mAuthenticatedUser.updateProfile(userBuilder.setDisplayName(user.getName()).build())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            updateUserInDB(user);
                        } else {
                            LocApplication.printShort(mContext, R.string.error_updating_profile);
                        }
                    }
                });
    }

    private void setEmail(final UserModel user) {
        mAuthenticatedUser.updateEmail(user.getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            updateUserInDB(user);
                        } else {
                            LocApplication.printShort(mContext, R.string.error_updating_profile);
                        }
                    }
                });
    }

    private void setPhoto(final UserModel user) {
        UserProfileChangeRequest.Builder userBuilder = new UserProfileChangeRequest.Builder();

        mAuthenticatedUser.updateProfile(userBuilder.setPhotoUri(user.getPhoto()).build())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // TODO: Select profile photo
                            updateUserInDB(user);
                        } else {
                            LocApplication.printShort(mContext, R.string.error_updating_profile);
                        }
                    }
                });
    }

    private void updateUserInDB(UserModel user) {
        String[] children = {DBHelper.USERS, user.getId()};
        DBHelper.setValue(children, user);
    }

    public void logout() {
        mAuth.signOut();
    }

    public static boolean isUserNameValid(String userName) {
        return (userName.length() > LocApplication.MIN_WORD_LENGTH);
    }


    public static boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    public static boolean isPasswordValid(String password) {
        return (password.length() > LocApplication.MIN_PASSWORD_LENGTH) && (password.matches(LocApplication.MATCH_LOWERCASE_CHARS) &&
                (password.matches(LocApplication.MATCH_NUMBERS)) && password.matches(LocApplication.MATCH_UPPERCASE_CHARS));
    }

}
