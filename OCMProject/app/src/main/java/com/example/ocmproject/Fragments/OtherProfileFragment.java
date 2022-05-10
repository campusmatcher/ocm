package com.example.ocmproject.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ocmproject.MainActivity;
import com.example.ocmproject.R;
import com.example.ocmproject.StartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class OtherProfileFragment extends Fragment {
    private FirebaseAuth auth;
    private DatabaseReference mRootRef;
    private DatabaseReference mDatabase;
    private String userId;
    private String name;
    private String surname;
    private String email;
    private TextView profileName;
    private TextView profileSurname;
    private TextView profileEmail;
    Button myInterestButton;
    Button logout;
    TableLayout myTable;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_profile, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        profileName = view.findViewById(R.id.profileName);
        profileSurname = view.findViewById(R.id.profileSurname);
        //profileEmail = findViewById(R.id.profileEmail);
        myInterestButton = view.findViewById(R.id.interest_button);
        myTable = view.findViewById(R.id.schTable);


        logout = view.findViewById(R.id.logoutButton);




        // Logout button's function
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "Logged Out!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), StartActivity.class);
                startActivity(intent);
            }
        });

        userId = auth.getCurrentUser().getUid();
        mDatabase.child("NewUser").child(userId).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.getValue(String.class);
                profileName.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mDatabase.child("NewUser").child(userId).child("surname").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                surname = snapshot.getValue(String.class);
                profileSurname.setText(surname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myInterestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InterestsFragment interestsFragment= new InterestsFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, interestsFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        mDatabase.child("NewUser").child(userId).child("Lessons").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                ArrayList<String> lessons = (ArrayList<String>) task.getResult().getValue();
                String[] schedule = new String[40];
                for (String lesson : lessons) {
                    mDatabase.child("Courses").child("Courses").child(lesson).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            ArrayList<String> hours = (ArrayList<String>)task.getResult().getValue();
                            for (int i = 0; i < hours.size(); i++) {
                                if (hours.get(i).equals("1")){
                                    schedule[i] = lesson;
                                }

                            }
                            if (lessons.indexOf(lesson) == lessons.size() - 1){
                                cleanTable(myTable);
                                for (int i=0; i < 8; i++) {
                                    TableRow row = new TableRow(getActivity());
                                    for (int j=0; j < 5; j++) {
                                        String value;
                                        if(schedule[j * 8 + i] != null) {
                                            value = schedule[j * 8 + i];
                                        }
                                        else{value = "free";}
                                        TextView tv = new TextView(getActivity());
                                        tv.setText(String.valueOf(value));
                                        row.addView(tv);
                                    }
                                    myTable.addView(row);
                                }
                            }
                        }
                    });
                }
            }
        });




        /*mDatabase.child("NewUser").child(userId).child("email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    email = task.getResult().getValue().toString();
                    profileEmail.setText(email);
                }
            }
        });*/


        return view;
    }
    private void cleanTable(TableLayout table) {

        int childCount = table.getChildCount();

        // Remove all rows except the first one
        if (childCount > 1) {
            table.removeViews(1, childCount - 1);
        }
    }
}