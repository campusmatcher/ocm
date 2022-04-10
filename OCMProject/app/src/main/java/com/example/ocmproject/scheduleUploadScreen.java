package com.example.ocmproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class scheduleUploadScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_upload_screen);
        Button scheduleUpload = findViewById(R.id.scheduleUploadButton);
        Intent intent = new Intent();
    }
}