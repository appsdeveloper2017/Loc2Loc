package com.appdesigndm.loc2loc;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appdesigndm.loc2loc.Helpers.AuthHelper;
import com.appdesigndm.loc2loc.Login.AccesActivity;
import com.appdesigndm.loc2loc.Login.LoginFragment;
import com.appdesigndm.loc2loc.MenuOptions.SettingsActivity;
import com.appdesigndm.loc2loc.MenuOptionsFloatBar.FragmentLinkUser;
import com.appdesigndm.loc2loc.MenuOptionsFloatBar.FragmentSelector;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.nio.channels.Selector;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, View.OnClickListener,FragmentLinkUser.OnFragmentInteractionListener {

    private GoogleMap mMap;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private View headerView;
    private ImageView userPhotoImageView;
    private TextView userPhotoInitialLetter;
    private TextView userNameTextView;
    private TextView userEmailTextView;
    private AuthHelper auth;
    private FABToolbarLayout fabToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.uno).setOnClickListener(this);
        findViewById(R.id.dos).setOnClickListener(this);
        findViewById(R.id.tres).setOnClickListener(this);
        findViewById(R.id.floating).setOnClickListener(this);


        //Declaración de los objetos del menú de barra.

        fabToolbarLayout =(FABToolbarLayout)findViewById(R.id.fabToolBarLayout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        init();
        PermissionUtils.mayRequestLocation(this, getCurrentFocus());

    }

    private void init() {
        auth = new AuthHelper(getApplicationContext());
        headerView = navigationView.getHeaderView(0);

        userPhotoImageView = (ImageView) headerView.findViewById(R.id.header_image_view);
        userPhotoInitialLetter = (TextView) headerView.findViewById(R.id.header_initial_letter);
        userNameTextView = (TextView) headerView.findViewById(R.id.header_user_name);
        userEmailTextView = (TextView) headerView.findViewById(R.id.header_user_email);
        if (auth.getAuthenticatedUser() != null) {
            setImage();
            userNameTextView.setText(auth.getAuthenticatedUser().getDisplayName());
            userEmailTextView.setText(auth.getAuthenticatedUser().getEmail());
        }
    }

    private void setImage() {
        if (auth.getPhoto() != null) {
//            userPhotoImageView.setBackground(getResources().getDrawable(R.drawable.circle_image_empty));
            userPhotoImageView.setImageURI(auth.getPhoto());
        } else {
            setInitialLetterToImage();
        }
    }

    private void setInitialLetterToImage() {
        String letter = auth.getName().substring(0, 1).toUpperCase();
//        letter = letter.substring(0, 1).toUpperCase();
//        letter = letter.toUpperCase();
//        userPhotoImageView.setBackground(getResources().getDrawable(R.drawable.circle_image_filled));
        userPhotoInitialLetter.setText(letter);
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (!PermissionUtils.isPermissionGranted(requestCode, permissions, grantResults)) {
            openAccesActivity();
        }
    }

    private void openAccesActivity() {
        Intent intent = new Intent(this, AccesActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_profile:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.nav_gallery:

                break;
            case R.id.nav_slideshow:

                break;
            case R.id.nav_manage:

                break;
            case R.id.nav_share:

                break;
            case R.id.nav_send:

                break;
            case R.id.nav_logout:
                logout();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        auth.logout();
        startActivity(new Intent(this, AccesActivity.class));
        finish();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Barcelona and move the camera
//
        LatLng position = new LatLng(41.382470, 2.177237);
        mMap.addMarker(new MarkerOptions().position(position));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LocApplication.printShort(MainActivity.this, auth.getAuthenticatedUser().getDisplayName());
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        //Método OnClick de la barra inferior.

        switch (v.getId()){
            case R.id.floating:
                fabToolbarLayout.show();
                break;
            case R.id.uno:
                FragmentSelector fragSelector = new FragmentSelector(1);
                Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
                intent.putExtra("Fragment",fragSelector.getSelectorFragment());
                startActivity(intent);
                fabToolbarLayout.hide();
                break;
            case R.id.dos:
                Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_SHORT).show();
                fabToolbarLayout.hide();
                break;
            case R.id.tres:
                Toast.makeText(getApplicationContext(),"3",Toast.LENGTH_SHORT).show();
                fabToolbarLayout.hide();
                break;
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}