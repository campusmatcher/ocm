package com.example.ocmproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class RegisterInterestActivity extends AppCompatActivity {

    TextView interests;
    boolean[] selectedInterest;
    ArrayList<Integer> interestList = new ArrayList<>();
    String[] interestArray = {"Football", "Arch Linux", "Science Fiction", "Anime","Vim","Taekwondo","Star Wars"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_interest);

        interests =findViewById(R.id.interests);

        selectedInterest = new boolean[interestArray.length];





        interests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializes dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        RegisterInterestActivity.this
                );
                builder.setTitle("Select Interest");

                builder.setCancelable(false);

                builder.setMultiChoiceItems(interestArray, selectedInterest, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            //When checkbox selected adds position into the interestList
                            interestList.add(i);
                            //Sorts the interestList
                            Collections.sort(interestList);
                        } else {
                            //When checkbox unselected remove position from interestList
                            interestList.remove(i);
                        }
                    }
                });

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();

                        for (int j = 0; j < interestList.size(); j++) {
                            stringBuilder.append(interestArray[interestList.get(j)]);

                            if (j != interestList.size() - 1) {
                                stringBuilder.append(", ");
                            }
                        }
                        interests.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j = 0; j < selectedInterest.length; j++) {
                            selectedInterest[j] = false;

                            interestList.clear();

                            interests.setText("");

                        }
                    }
                });
                builder.show();
            }
        });
    }
}