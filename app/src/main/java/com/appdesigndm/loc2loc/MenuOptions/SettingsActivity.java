package com.appdesigndm.loc2loc.MenuOptions;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.appdesigndm.loc2loc.MenuOptionsFloatBar.FragmentLinkUser;
import com.appdesigndm.loc2loc.MenuOptionsFloatBar.FragmentListUsers;
import com.appdesigndm.loc2loc.Models.UserModel;
import com.appdesigndm.loc2loc.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements FragmentLinkUser.OnFragmentInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private int parameterBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow_white);
        setActionBarTitle(getResources().getString(R.string.toolbar_title_profile_fragment));

        //Recover extras, if extras !null select a Fragment.
        try {
            Bundle bundle = getIntent().getExtras();
            parameterBundle = bundle.getInt("Fragment");

            if (bundle != null)
                switch (parameterBundle) {
                    case 1:
                        setActionBarTitle(getResources().getString(R.string.user_link));
                        getSupportFragmentManager().beginTransaction().add(R.id.settings_container, new FragmentLinkUser()).commit();
                        break;
                    case 2:
                        setActionBarTitle(getResources().getString(R.string.mylinks));
                        getSupportFragmentManager().beginTransaction().add(R.id.settings_container, new FragmentListUsers()).commit();
                        break;

                    case 3:

                        break;
                    default:
                }
        } catch (NullPointerException e) {
            getSupportFragmentManager().beginTransaction().add(R.id.settings_container, new EditProfileFragment()).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
