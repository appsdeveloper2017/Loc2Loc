package com.appdesigndm.loc2loc.MenuOptions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appdesigndm.loc2loc.Components.ProfilePhotoComponent;
import com.appdesigndm.loc2loc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {

    @BindView(R.id.profile_photo)
    ProfilePhotoComponent photo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        ((SettingsActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.toolbar_title_profile_fragment));
        photo.setPhoto(R.drawable.chincheta);

        return view;
    }

}