package com.example.ocmproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    private Button register;
    private Button login;
    private ImageView iconImageLeft;
    private ImageView iconImageRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        iconImageLeft = findViewById(R.id.iconImageLeft);
        iconImageRight = findViewById(R.id.iconImageRight);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                //finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        TranslateAnimation animationLeft = new TranslateAnimation(-1000,0,0, 0);
        animationLeft.setDuration(2000);
        animationLeft.setFillAfter(false);
        animationLeft.setAnimationListener(new MyAnimationListener());

        iconImageLeft.setAnimation(animationLeft);

        TranslateAnimation animationRight = new TranslateAnimation(1000,0,0, 0);
        animationRight.setDuration(2000);
        animationRight.setFillAfter(false);
        animationRight.setAnimationListener(new MyAnimationListener());

        iconImageRight.setAnimation(animationRight);
    }

    // Always sign in part
    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
        }
    }

    private class MyAnimationListener implements Animation.AnimationListener{


        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            iconImageLeft.clearAnimation();
            iconImageRight.clearAnimation();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}


