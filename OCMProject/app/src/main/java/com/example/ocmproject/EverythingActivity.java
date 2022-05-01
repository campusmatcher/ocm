package com.example.ocmproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class EverythingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_everything);
        Button manualScheduleUploadButton = findViewById(R.id.manualScheduleUploadButton);
        Button geciciButon = findViewById(R.id.profileButton);
        Button geciciButon2 = findViewById(R.id.connectionsButton);
        Button realMainButton = findViewById(R.id.realMainButton);
        Button interestsButton = findViewById(R.id.interestsButton);
        Button oldMainButton = findViewById(R.id.oldMainButton);


        geciciButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EverythingActivity.this, ProfileActivity.class));
            }
        });
        geciciButon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EverythingActivity.this, CollectionsActivity.class));
            }
        });

        realMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EverythingActivity.this, RealMainActivity.class));
            }
        });

        interestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EverythingActivity.this, InterestsActivity.class));
            }
        });

        oldMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EverythingActivity.this, MainActivity.class));
            }
        });
    }
}