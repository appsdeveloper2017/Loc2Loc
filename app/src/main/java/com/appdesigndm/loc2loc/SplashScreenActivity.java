package com.appdesigndm.loc2loc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.appdesigndm.loc2loc.Helpers.AuthHelper;
import com.appdesigndm.loc2loc.Login.AccesActivity;
import com.appdesigndm.loc2loc.MenuOptions.SettingsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SplashScreenActivity extends AppCompatActivity {

    // UI references
    private ImageView imageSplash;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mContext = this;

        init();
        final AuthHelper auth = new AuthHelper(getApplicationContext());
        abrirActivityProvisional(auth);
//        animateOnCreateViews();
    }

    private void abrirActivityProvisional(AuthHelper auth) {
        auth.getAuth()
                .signInWithEmailAndPassword("a@a.com", "Aa1234")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(mContext, MainActivity.class));
                            finish();
                        } else {
                            LocApplication.printShort(mContext, "Error de login");
                        }
                    }
                });
    }

    private void init() {
//        LocApplication.fDatabase = FirebaseDatabase.getInstance();
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
                AuthHelper auth = new AuthHelper();
                if (auth.getCurrentUser() != null) {
                    intent.setClass(SplashScreenActivity.this, MainActivity.class);
                } else {
                    intent.setClass(SplashScreenActivity.this, AccesActivity.class);
                }
//                intent.setClass(SplashScreenActivity.this, SettingsActivity.class);
                startActivity(intent);
                finish();

            }
        }, 100);

    }
}
