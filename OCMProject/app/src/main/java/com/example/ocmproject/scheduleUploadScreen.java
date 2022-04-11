package com.example.ocmproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.core.Core;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


import java.io.FileNotFoundException;

public class scheduleUploadScreen extends AppCompatActivity {

    // Yavuz was here
    // Also alp was
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_upload_screen);
        Button scheduleUploadButton = findViewById(R.id.scheduleUploadButton);
        OpenCVLoader.initDebug();

        scheduleUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });
    }

    // The image is shown
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            Uri imageChoosen = data.getData();
            Log.i("Image string", data.toString());
            ImageView scheduleView = findViewById(R.id.scheduleView);
            scheduleView.setImageURI(imageChoosen);
            //scheduleView.setImageBitmap();
            Bitmap photo = null;
            try {
                photo = (Bitmap) MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageChoosen);
            }
            catch (Exception e)
            {
                Log.e("Exception", e.toString());
            }
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // must be in main
            ScheduleReader scheduler = new ScheduleReader(photo);
            scheduler.grayscaleImage();
            scheduler.blurImage();
            scheduler.blurImage();
            scheduler.thresholdImage();
            scheduler.findContours();
            scheduler.findBoxes();
            scheduler.omitBigBoxes(scheduler.getBoxes());
            scheduler.omitSizeBoxes(scheduler.getBoxes());
            scheduler.paintBoxes();
            scheduler.paintBoxes();
            scheduler.readText();
            //scheduler.newThread();
        }

    }
}












