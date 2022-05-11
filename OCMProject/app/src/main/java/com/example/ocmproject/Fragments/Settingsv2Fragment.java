package com.example.ocmproject.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ocmproject.R;
import com.example.ocmproject.RegisterInterestActivity;
import com.example.ocmproject.ScheduleActivity;
import com.example.ocmproject.StartActivity;
import com.google.firebase.auth.FirebaseAuth;


public class Settingsv2Fragment extends Fragment {

    Button logOut;
    Button uploadSchedule;
    Button addInterest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_settingsv2, container, false);

        logOut = view.findViewById(R.id.logOut);
        addInterest = view.findViewById(R.id.addInterest);
        uploadSchedule = view.findViewById(R.id.backProfile);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "Logged Out!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), StartActivity.class);
                startActivity(intent);
            }
        });
        addInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RegisterInterestActivity.class));
            }
        });
        uploadSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ScheduleActivity.class));
            }
        });

        return view;
    }
}