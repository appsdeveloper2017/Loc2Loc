package com.appdesigndm.loc2loc.MenuOptions;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.appdesigndm.loc2loc.Components.CustomDialog;
import com.appdesigndm.loc2loc.Helpers.AuthHelper;
import com.appdesigndm.loc2loc.Components.ProfilePhotoComponent;
import com.appdesigndm.loc2loc.Components.ViewProfileComponent;
import com.appdesigndm.loc2loc.Helpers.DBHelper;
import com.appdesigndm.loc2loc.Models.ErrorModel;
import com.appdesigndm.loc2loc.R;
import com.appdesigndm.loc2loc.Models.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileFragment extends Fragment {

    public static final String TAG = "Loc2Loc_Error:";

    @BindView(R.id.edit_profile_progress)
    ProgressBar progressBar;

    @BindView(R.id.ly_edit_profile)
    ConstraintLayout layoutData;

    @BindView(R.id.profile_photo)
    ProfilePhotoComponent photo;

    @BindView(R.id.edit_name)
    ViewProfileComponent name;

    @BindView(R.id.edit_mail)
    ViewProfileComponent mail;

    private DatabaseReference dbr;
    private ValueEventListener listener;
    private CustomDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, view);

//        dialog = CustomDialog.newInstance("Un titulo",
//                null, "Una descripción", null, "Aceptar");
        dialog = new CustomDialog();
//        dialog.setTitle("Un titulo")
//                .setSeverity(CustomDialog.Severity.ERROR)
//                .setDescription("Una descripción")
//                .setLeftButton("Izq", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        })
//        .setRightButton("Dcha", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
        dialog.show(getFragmentManager(), null);

//        init();
        return view;
    }

    private void init() {
        ((SettingsActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.toolbar_title_profile_fragment));
        AuthHelper auth = new AuthHelper();
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

    public ValueEventListener getDBUserListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                loadData(user);
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

    private void loadData(UserModel user) {
        photo.setPhoto(R.drawable.chincheta);

        name.setIcon(R.drawable.ic_menu_profile);
        name.setTitle(getString(R.string.name));
        name.setText(user.getName());

        mail.setIcon(R.drawable.ic_menu_mail);
        mail.setTitle(getString(R.string.prompt_email));
        mail.setText(user.getEmail());
    }
}