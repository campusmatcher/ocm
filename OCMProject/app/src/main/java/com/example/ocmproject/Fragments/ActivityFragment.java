package com.example.ocmproject.Fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ocmproject.R;
import com.example.ocmproject.RegisterInterestActivity;
import com.example.ocmproject.ScheduleActivity;
import com.example.ocmproject.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class ActivityFragment extends Fragment {
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private String userId;
    private ListView listView;
    private ArrayList<String> list;
    private ArrayAdapter adapter;

    private Button selectDate;
    private DatePickerDialog datePickerDialog;
    private TextView dateTxt;
    private Calendar calendar;
    private int year, month, dayOfMonth;

    TextView interests;
    boolean[] selectedInterest;
    Button nextButton;
    ArrayList<Integer> interestIndexList = new ArrayList<>();
    ArrayList<String> interestList = new ArrayList<>();
    String[] interestArray = {"Study CS 102", "Chess", "Cinema", "Football Match","Coding","Eating Lunch/Dinner","Study CS 102 More"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_activity, container, false);

        auth = FirebaseAuth.getInstance();
        listView = view.findViewById(R.id.listView);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = auth.getCurrentUser().getUid();
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_layout, list);
        listView.setAdapter(adapter);
        interests = view.findViewById(R.id.interests);


        selectedInterest = new boolean[interestArray.length];

        Button nextButton = view.findViewById(R.id.nextButton);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        selectDate = view.findViewById(R.id.buttonDate);
        dateTxt = view.findViewById(R.id.dateTxt);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dateTxt.setText(day + "/" + (month+1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        interests.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Initializes dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        getActivity()
                );
                builder.setTitle("Select Activity");

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


       return view;
    }
}