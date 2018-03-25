package com.appdesigndm.loc2loc.MenuOptions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appdesigndm.loc2loc.Components.ProfilePhotoComponent;
import com.appdesigndm.loc2loc.Components.ViewProfileComponent;
import com.appdesigndm.loc2loc.LocApplication;
import com.appdesigndm.loc2loc.R;
import com.appdesigndm.loc2loc.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileFragment extends Fragment {

    public static final String TAG = "Loc2Loc_Error:";
    
    @BindView(R.id.profile_photo)
    ProfilePhotoComponent photo;

    @BindView(R.id.edit_name)
    ViewProfileComponent name;

    @BindView(R.id.edit_mail)
    ViewProfileComponent mail;

    UserModel user;

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
        user = LocApplication.fDatabase.getReference().addListenerForSingleValueEvent();
        photo.setPhoto(R.drawable.chincheta);

        name.setIcon(R.drawable.ic_menu_profile);
        name.setTitle(getString(R.string.name));
        name.setText(user.getName());

        mail.setIcon(R.drawable.ic_menu_mail);
        mail.setTitle(getString(R.string.prompt_email));
        mail.setText(user.getEmail());
    }

    public ValueEventListener getDBUserListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = (UserModel) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "Cancelled reading DB: " + databaseError.toString());
            }
        };
    }
}