package com.appdesigndm.loc2loc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SplashScreen extends AppCompatActivity {

    // UI references
    private ImageView imageSplash;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mContext = this;

        init();
        animateOnCreateViews();
        LocApplication.fAuth = FirebaseAuth.getInstance();
        LocApplication.fDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        LocApplication.fCurrentUser = LocApplication.fAuth.getCurrentUser();
        String id = LocApplication.fCurrentUser.getUid();
    }

    private void init() {
        imageSplash = (ImageView) findViewById(R.id.imagen_splash);
    }

    private void animateOnCreateViews() {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.animation_splash_icon);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                splashScrennDelay();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageSplash.startAnimation(animation);
    }

    private void splashScrennDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
//                if (LocApplication.fCurrentUser != null) {
//                    intent.setClass(SplashScreen.this, MainActivity.class);
//                } else {
                    intent.setClass(SplashScreen.this, LoginActivity.class);
//                }
                startActivity(intent);
                finish();

            }
        }, 500);

    }
}
