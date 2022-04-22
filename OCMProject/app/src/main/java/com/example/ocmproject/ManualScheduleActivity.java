package com.example.ocmproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ManualScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_schedule);
        Button goBackButton = findViewById(R.id.manualScheduleGoBackButton);
        ImageButton manualScheduleScreenNextButton = findViewById(R.id.manualScheduleNextButton);

        manualScheduleScreenNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManualScheduleActivity.this, ScheduleActivity.class));
            }
        });

    }
}