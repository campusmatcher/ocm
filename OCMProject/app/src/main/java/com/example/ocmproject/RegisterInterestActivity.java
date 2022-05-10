package com.example.ocmproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class RegisterInterestActivity extends AppCompatActivity {

    TextView interests;
    boolean[] selectedInterest;
    Button nextButton;
    ArrayList<Integer> interestIndexList = new ArrayList<>();
    ArrayList<String> interestList = new ArrayList<>();
    String[] interestArray = {"Football", "Arch Linux", "Science Fiction", "Anime","Vim","Taekwondo","Star Wars"};
    FirebaseAuth auth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_interest);

        interests =findViewById(R.id.interests);

        selectedInterest = new boolean[interestArray.length];

        Button nextButton = findViewById(R.id.nextButton);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("NewUser").child(auth.getCurrentUser().getUid()).child("Interests").setValue(interestArray);
                startActivity(new Intent(RegisterInterestActivity.this, ScheduleActivity.class));
                finish();
            }
        });

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
                            //When checkbox selected adds position into the interestIndexList
                            interestIndexList.add(i);
                            //Sorts the interestIndexList
                            Collections.sort(interestIndexList);
                        } else {
                            //When checkbox unselected remove position from interestIndexList
                            interestIndexList.remove(i);
                        }
                    }
                });

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();

                        for (int j = 0; j < interestIndexList.size(); j++) {
                            stringBuilder.append(interestArray[interestIndexList.get(j)]);
                            interestList.add(interestArray[interestIndexList.get(j)]);

                            if (j != interestIndexList.size() - 1) {
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

                            interestIndexList.clear();

                            interests.setText("");

                        }
                    }
                });
                builder.show();
            }
        });
    }
}