package com.appdesigndm.loc2loc.Login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.appdesigndm.loc2loc.LocApplication;
import com.appdesigndm.loc2loc.MainActivity;
import com.appdesigndm.loc2loc.R;
import com.appdesigndm.loc2loc.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

import static android.Manifest.permission.READ_CONTACTS;

public class RegisterFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private Context mContext;

    // UI references.
    @BindView(R.id.register_user_name)
    public EditText mUserNameView;
    @BindView(R.id.register_email)
    public AutoCompleteTextView mEmailView;
    @BindView(R.id.register_repeat_email)
    public AutoCompleteTextView mRepeatEmailView;
    @BindView(R.id.register_password)
    public EditText mPasswordView;
    @BindView(R.id.register_progress)
    public ProgressBar mProgressView;
    @BindView(R.id.register_form)
    public ScrollView mRegisterFormView;
    @BindView(R.id.register_email_sign_in_button)
    public Button mEmailSignInButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        ButterKnife.bind(this, v);
        populateAutoComplete();
        return v;
    }

    @OnEditorAction(R.id.register_password)
    public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
            attemptRegister();
            return true;
        }
        return false;
    }

    @OnClick(R.id.register_email_sign_in_button)
    public void onClick(View view) {
        attemptRegister();
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (getContext().checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the register form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual register attempt is made.
     */
    private void attemptRegister() {
        resetErrors();

        // Store values at the time of the register attempt.
        final String userName = mUserNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String repeatEmail = mRepeatEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid user name.
        if (TextUtils.isEmpty(userName)) {
            mUserNameView.setError(getString(R.string.error_field_register_required));
            focusView = mUserNameView;
            cancel = true;
        } else if (!isUserNameValid(userName)) {
            mUserNameView.setError(getString(R.string.error_invalid_register_user_name));
            focusView = mUserNameView;
            cancel = true;
        } else {
            // Check for a valid email address.
            if (TextUtils.isEmpty(email)) {
                mEmailView.setError(getString(R.string.error_field_register_required));
                focusView = mEmailView;
                cancel = true;
            } else if (!isEmailValid(email)) {
                mEmailView.setError(getString(R.string.error_invalid_register_email));
                focusView = mEmailView;
                cancel = true;
            } else {
                // Check if the mail is equals than the previous address
                if (!email.equals(repeatEmail)) {
                    mRepeatEmailView.setError(getString(R.string.error_invalid_register_repeat_email));
                    focusView = mRepeatEmailView;
                    cancel = true;
                } else {
                    // Check for a valid password, if the user entered one.
                    if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                        mPasswordView.setError(getString(R.string.error_invalid_register_password));
                        focusView = mPasswordView;
                        cancel = true;
                    } else if (TextUtils.isEmpty(password)) {
                        mPasswordView.setError(getString(R.string.error_field_register_required));
                        focusView = mPasswordView;
                        cancel = true;
                    }
                }
            }
        }

        if (cancel) {
            // There was an error; don't attempt register and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user register attempt.
            showProgress(true);
            LocApplication.fAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                LocApplication.fCurrentUser = LocApplication.fAuth.getCurrentUser();
                                updateAuthData(userName);
                            } else {
                                showRegisterError(task);
                                showProgress(false);
                            }
                        }
                    });
        }
    }

    private void updateAuthData(final String userName) {
        // Update Auth profile
        UserProfileChangeRequest.Builder userBuilder = new UserProfileChangeRequest.Builder();
        LocApplication.fCurrentUser.updateProfile(userBuilder.setDisplayName(userName).build())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // TODO: Select profile photo
                        LocApplication.fCurrentUser = LocApplication.fAuth.getCurrentUser();
                        // Update DDBB
                        DatabaseReference dbRef = LocApplication.fDatabase.getReference();
                        User user = new User(userName, LocApplication.fCurrentUser.getEmail(), LocApplication.fCurrentUser.getUid());
                        dbRef.child(LocApplication.USERS).child(user.getId()).setValue(user);
                        showProgress(false);
                        openMainActivity();
                    }
                });
    }

    private void showRegisterError(@NonNull Task<AuthResult> task) {
        Toast t = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
            t.setText(R.string.error_user_already_exists);
        } else if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
            t.setText(R.string.error_weak_password);
        } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
            t.setText(R.string.error_malformed_mail);
        } else {
            t.setText(R.string.error_connection);
        }
        t.setGravity(Gravity.CENTER, 0, 0);
        t.show();

    }

    private void openMainActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void resetErrors() {
        mUserNameView.setError(null);
        mEmailView.setError(null);
        mRepeatEmailView.setError(null);
        mPasswordView.setError(null);
    }

    private boolean isUserNameValid(String userName) {
        return (userName.length() > LocApplication.MIN_WORD_LENGTH);
    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    private boolean isPasswordValid(String password) {
        return (password.length() > LocApplication.MIN_WORD_LENGTH) && (password.matches(LocApplication.MATCH_LOWERCASE_CHARS) &&
                (password.matches(LocApplication.MATCH_NUMBERS)) && password.matches(LocApplication.MATCH_UPPERCASE_CHARS));
    }

    /**
     * Shows the progress UI and hides the register form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getContext(),
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


}