package com.appdesigndm.loc2loc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.appdesigndm.loc2loc.Login.LoginFragment;

public class AccesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acces);

        getSupportFragmentManager().beginTransaction().add(R.id.acces_container, new LoginFragment()).commit();
    }
}
