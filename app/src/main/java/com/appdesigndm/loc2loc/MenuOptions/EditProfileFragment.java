package com.appdesigndm.loc2loc.MenuOptions;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.appdesigndm.loc2loc.Callbacks.OnFinish;
import com.appdesigndm.loc2loc.Components.EditDataDialog;
import com.appdesigndm.loc2loc.Components.EditPasswordDialog;
import com.appdesigndm.loc2loc.Components.ProfilePhotoComponent;
import com.appdesigndm.loc2loc.Components.ViewProfileComponent;
import com.appdesigndm.loc2loc.Helpers.AuthHelper;
import com.appdesigndm.loc2loc.Helpers.DBHelper;
import com.appdesigndm.loc2loc.LocApplication;
import com.appdesigndm.loc2loc.Models.ErrorModel;
import com.appdesigndm.loc2loc.Models.UserModel;
import com.appdesigndm.loc2loc.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileFragment extends Fragment {

    public static final String TAG = "Loc2Loc_Error:";
    public static final String TAG_DIALOG = "Edit_Dialog";

    @BindView(R.id.edit_profile_progress)
    ProgressBar progressBar;

    @BindView(R.id.ly_edit_profile)
    ConstraintLayout layoutData;

    @BindView(R.id.profile_photo)
    ProfilePhotoComponent photo;

    @BindView(R.id.view_name)
    ViewProfileComponent viewName;

    @BindView(R.id.view_mail)
    ViewProfileComponent viewMail;

    @BindView(R.id.view_password)
    ViewProfileComponent viewPassword;

    private DatabaseReference dbr;
    private ValueEventListener listener;
    private UserModel user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void init() {
        ((SettingsActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.toolbar_title_profile_fragment));
        AuthHelper auth = new AuthHelper(getContext());
        if (auth.getAuthenticatedUser() != null) {
            dbr = DBHelper.getInstance();
            showProgressBar();
            listener = getDBUserListener();
            dbr.child(DBHelper.USERS)
                    .child(auth.getId()).addValueEventListener(listener);
            photo.setEditable(true);
            photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                }
            });
        }
    }

    public ValueEventListener getDBUserListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(UserModel.class);
                loadData();
                hideProgressBar();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Cancelled reading DB: " + databaseError.toString());
                if (getActivity() != null) {
                    ErrorModel error = new ErrorModel(getContext());
                    error.setPositiveListener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().finish();
                        }
                    }).show();
                }
                hideProgressBar();
            }
        };
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        layoutData.setVisibility(View.GONE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        layoutData.setVisibility(View.VISIBLE);
        dbr.removeEventListener(listener);
    }

    private void loadData() {
        photo.setPhoto(R.drawable.chincheta);

        viewName.setIcon(R.drawable.ic_menu_profile);
        viewName.setTitle(getString(R.string.name));
        //viewName.setText(user.getName()); Borrar linea de abajo.
        viewName.setText("Alex");

        viewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchEditName();
            }
        });

        viewMail.setIcon(R.drawable.ic_menu_mail);
        viewMail.setTitle(getString(R.string.prompt_email));
        //viewMail.setText(user.getEmail()); // Borrar linea de abajo.
        viewMail.setText("mail@mail.com");
        viewMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchEditMail();
            }
        });

        viewPassword.setIcon(R.drawable.ic_menu_password);
        viewPassword.setTitle(getString(R.string.prompt_password));
        viewPassword.setText(getString(R.string.prompt_password));
        viewPassword.ofuscateText();
        viewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchEditPassword();
            }
        });
    }

    private void launchEditName() {
        final EditDataDialog dialog = new EditDataDialog();
        dialog.setTitle(getString(R.string.edit_name))
                .setDescription(user.getName())
                .setRightButtonText(getString(R.string.accept))
                .setRightButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newName = dialog.getDescriptionContent();
                        if (AuthHelper.isUserNameValid(newName)) {
                            user.setName(newName);
                            saveData(AuthHelper.NAME);
                            dialog.dismiss();
                        } else {
                            LocApplication.printShort(getContext(), R.string.error_invalid_register_user_name);
                        }
                    }
                }).show(getFragmentManager(), TAG_DIALOG);
    }

    private void launchEditMail() {
        final EditDataDialog dialog = new EditDataDialog();
        dialog.setTitle(getString(R.string.edit_mail))
                .setDescription(user.getEmail())
                .setRightButtonText(getString(R.string.accept))
                .setRightButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newEmail = dialog.getDescriptionContent();
                        if(AuthHelper.isEmailValid(newEmail)) {
                            user.setEmail(newEmail);
                            saveData(AuthHelper.EMAIL);
                            dialog.dismiss();
                        } else {
                            LocApplication.printShort(getContext(), R.string.error_malformed_mail);
                        }
                    }
                }).show(getFragmentManager(), TAG_DIALOG);
    }

    private void launchEditPassword() {
        final EditPasswordDialog dialog = new EditPasswordDialog();
        dialog.setLeftButtonText(getString(R.string.cancel))
                .setLeftButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                })
                .setRightButtonText(getString(R.string.accept))
                .setRightButtonListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (AuthHelper.isPasswordValid(dialog.getNewPassword())) {
                            savePassword(dialog);
                                                                               dialog.dismiss();
                        } else {
                            LocApplication.printShort(getContext(), R.string.error_invalid_register_password);
                        }
                    }
                })
                .show(getFragmentManager(), TAG_DIALOG);
    }

    private void savePassword(final EditPasswordDialog dialog) {
        dialog.setOnFinish(new OnFinish() {
            @Override
            public void run(OnFinishResultCode resultCode) {
                if (resultCode == OnFinishResultCode.SUCCES) {
                    dialog.dismiss();
                } else {
                    LocApplication.printShort(getContext(), R.string.error_invalid_register_password);
                }
            }
        });
        AuthHelper auth = new AuthHelper(getContext());
        auth.updatePassword(dialog.getOldPassword(), dialog.getNewPassword(), dialog);
    }

    private void saveData(String field) {
        AuthHelper auth = new AuthHelper(getContext());
        auth.updateProfile(user, field);
        LocApplication.printShort(getContext(), user.toString());
        loadData();
    }

}