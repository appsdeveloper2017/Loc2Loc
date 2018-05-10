package com.appdesigndm.loc2loc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LinkUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.linkUser_title);
        setContentView(R.layout.activity_link_user);
    }
}
