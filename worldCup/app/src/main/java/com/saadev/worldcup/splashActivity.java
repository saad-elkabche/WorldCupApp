package com.saadev.worldcup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class splashActivity extends AppCompatActivity {
    ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        imgLogo=findViewById(R.id.imgLogo);

        Animation anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.myanimation);
        imgLogo.startAnimation(anim);

      new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(splashActivity.this,MainActivity.class));
                finish();
            }
        },4000);
    }
}