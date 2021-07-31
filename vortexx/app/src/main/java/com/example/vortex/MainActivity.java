package com.example.vortex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 7100;
    Animation topAnimation, bottomAnimation, middleAnimation;
    TextView lblUno, lblDos, lblTres;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);


        lblUno = (TextView)findViewById(R.id.lblUno);
        lblDos = (TextView)findViewById(R.id.lblDos);
        lblTres = (TextView)findViewById(R.id.lblTres);


        lblUno.setAnimation(topAnimation);
        lblDos.setAnimation(middleAnimation);
        lblTres.setAnimation(bottomAnimation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, index.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);


    }
}