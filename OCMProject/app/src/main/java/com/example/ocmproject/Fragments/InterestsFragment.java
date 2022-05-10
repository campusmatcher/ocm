package com.example.ocmproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ocmproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class InterestsFragment extends Fragment {

    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private String userId;
    private ListView listView;
    private ArrayList<String> interestsList;
    private ArrayAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_interests, container, false);

        auth = FirebaseAuth.getInstance();
        listView = view.findViewById(R.id.listView);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = auth.getCurrentUser().getUid();
        interestsList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_layout, interestsList);
        listView.setAdapter(adapter);



        mDatabase.child("Users").child(userId).child("Interests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                interestsList.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String interest = snap.getValue(String.class);
                    //String interestName = interest.getName();
                    interestsList.add(interest);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}