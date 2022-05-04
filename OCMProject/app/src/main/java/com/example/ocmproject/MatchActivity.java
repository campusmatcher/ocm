package com.example.ocmproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MatchActivity extends AppCompatActivity {
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
    private ArrayList<User> list;
    private UserListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = auth.getCurrentUser().getUid();
        list = new ArrayList<>();
        adapter = new UserListAdapter(this, list);
        ListView matchList = findViewById(R.id.matchList);

        matchList.setAdapter(adapter);

        mDatabase.child("Users").child(userId).child("Matches").addValueEventListener(new ValueEventListener() {
        //mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String friendId = snap.getValue(String.class);
                    //Iterable<Object> = mDatabase.child("Users").child(userId).child("Contacts").get
                    DatabaseReference friendRefs = FirebaseDatabase.getInstance().getReference().child("Users").child(friendId);
                    friendRefs.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapsho) {
                            User friendUserObj = snapsho.getValue(User.class);
                            String friendNameSurname = friendUserObj.getName() + " " + friendUserObj.getSurname();
                            list.add(friendUserObj);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}