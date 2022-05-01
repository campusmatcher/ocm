package com.example.ocmproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ocmproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        profileName = view.findViewById(R.id.profileName);
        profileSurname = view.findViewById(R.id.profileSurname);
        profileEmail = view.findViewById(R.id.profileEmail);

        userId = auth.getCurrentUser().getUid();
        mDatabase.child("Users").child(userId).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    name = task.getResult().getValue().toString();
                    profileName.setText(name);
                }
            }
        });
        mDatabase.child("Users").child(userId).child("surname").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    surname = task.getResult().getValue().toString();
                    profileSurname.setText(surname);
                }
            }
        });
        mDatabase.child("Users").child(userId).child("email").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
        });

        return view;
    }
}