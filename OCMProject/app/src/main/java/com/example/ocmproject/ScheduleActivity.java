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
import android.widget.ImageButton;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;

public class ScheduleActivity extends AppCompatActivity {

    // Yavuz was here
    // Also alp was
    // So was Arda
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_upload_screen);

        Button scheduleUploadButton = findViewById(R.id.scheduleUploadButton);
        Button manualScheduleUploadButton = findViewById(R.id.manualScheduleUploadButton);
        ImageButton nextButton = findViewById(R.id.scheduleToMainButton);


        OpenCVLoader.initDebug();
//        for(String course: courses){
//            System.out.println(course);

        scheduleUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });

        manualScheduleUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScheduleActivity.this, ManualScheduleActivity.class));
            }
        });



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScheduleActivity.this, RegisterActivity.class));
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
            scheduler.runReader();
//            ArrayList<String> sections = scheduler.runReader();
//            sections.add("Math101-01");
//            sections.add("Eng101-01");
//            sections.add("Cs101-01");
//            sections.add("Turk101-01");
//            sections.add("Math132-01");
            //for (String course: sections){


            //SOme stuff to adjust arraylist
//            for (String course: sections){
//                int i = 0;
//                String courseName = "";
//                String courseCode = "";
//                String sectionCode = "";
//                for (; i < course.length() && Character.isLetter(course.charAt(i)); i++){ courseName += course.charAt(i);}
//                for (; i < course.length() && course.charAt(i) != '-'; i++){ courseCode += course.charAt(i);}
//                sectionCode = course.substring(i);
//
//            }
//                for (Section sect: sections){
//                    if (sect.getHashMap().get(key) == 1){
//                        this.hmap.put(key, 1);
//                    }
//                    else if (sect.getHashMap().get(key) == 2){ // for spare hours
//                        this.hmap.put(key, 2);

            //Log.e("lenght", "" + sections.size());
            //scheduler.newThread();
        }


    }

}