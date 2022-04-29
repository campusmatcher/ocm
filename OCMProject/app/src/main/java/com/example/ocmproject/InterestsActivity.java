package com.example.ocmproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InterestsActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private String userId;
    private ListView listView;
    private ArrayList<String> interestsList;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        auth = FirebaseAuth.getInstance();
        listView = findViewById(R.id.listView);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = auth.getCurrentUser().getUid();
        interestsList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.list_layout, interestsList);
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

    }
}