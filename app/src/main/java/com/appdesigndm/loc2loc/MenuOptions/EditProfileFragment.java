package com.appdesigndm.loc2loc.MenuOptions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appdesigndm.loc2loc.Components.ProfilePhotoComponent;
import com.appdesigndm.loc2loc.Components.ViewProfileComponent;
import com.appdesigndm.loc2loc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileFragment extends Fragment {

    @BindView(R.id.profile_photo)
    ProfilePhotoComponent photo;

    @BindView(R.id.edit_name)
    ViewProfileComponent name;

    @BindView(R.id.edit_mail)
    ViewProfileComponent mail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, view);

        ((SettingsActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.toolbar_title_profile_fragment));

        init();


        return view;
    }

    private void init() {
        photo.setPhoto(R.drawable.chincheta);

        name.setIcon(R.drawable.ic_menu_profile);
        name.setTitle("Nombre y apellidos");
        name.setText("David Mendaño");

        mail.setIcon(R.drawable.ic_menu_mail);
        mail.setTitle("Email");
        mail.setText("a@a.com");
    }

}