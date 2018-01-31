package com.appdesigndm.loc2loc;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private TextView titulo_splash;
    private TextView subtitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        titulo_splash = (TextView)findViewById(R.id.titulo_splash);
        subtitulo = (TextView)findViewById(R.id.subtitulo);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);

            }
        },1500);
    }
}
