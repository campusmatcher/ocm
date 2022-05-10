package com.example.ocmproject.match;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ocmproject.R;
import com.example.ocmproject.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Matchv2Activity extends AppCompatActivity implements MatchAdapterv2.ItemClickListener {
    FirebaseAuth auth;
    DatabaseReference mDatabase;
    ArrayList<User> list;
    MatchAdapterv2 adapter2;
    RecyclerView recycleView;
    Matcherv2 matcher2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchv2);

        //Database
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Create matcher object and update matchlist
        matcher2 = new Matcherv2();
        matcher2.findAndUpdateMatchList();

        //Create an user list and bind adapter
        list = new ArrayList<>();
        adapter2 = new MatchAdapterv2(this, list);
        recycleView = findViewById(R.id.matchRecycler);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter2.setClickListener(this);
        recycleView.setAdapter(adapter2);

        //Populating recyclerview with data form firebase
        mDatabase.child("NewUser").child(auth.getUid()).child("MatchList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String friendId = snap.getValue(String.class);
                    //Iterable<Object> = mDatabase.child("Users").child(userId).child("Contacts").get
                    DatabaseReference friendRefs = FirebaseDatabase.getInstance().getReference().child("NewUser").child(friendId);
                    friendRefs.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull  Task<DataSnapshot> task) {
                            if (!task.isSuccessful()){
                                Log.e("Error in retrieving user object for matching", task.toString());

                            }
                            else {
                                DataSnapshot snapsho = task.getResult();
                                User friendUserObj = snapsho.getValue(User.class);
                                list.add(friendUserObj);
                                adapter2.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error in retrieving matching info", error.getMessage());

            }
        });

    }

    //method defining click action
    @Override
    public void onItemClick(View view, int position) {
        switch(view.getId()){
            case R.id.addButton:
                Toast.makeText(this, "You clicked " + adapter2.getItem(position) + " on row number " + position + view.getId(), Toast.LENGTH_SHORT).show();
                mDatabase.child("NewUser").child(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(!task.isSuccessful()){
                            ///
                        }
                        else{
                            User current = task.getResult().getValue(User.class);
                            if(current.getPending().contains(adapter2.getItem(position).getId())){
                                current.acceptContact(adapter2.getItem(position).getId());
                                Toast.makeText(Matchv2Activity.this, "a", Toast.LENGTH_SHORT).show();
                                view.setEnabled(false);
                            }
                            else{
                                current.addItemToSent(adapter2.getItem(position).getId());
                                Toast.makeText(Matchv2Activity.this, "b", Toast.LENGTH_SHORT).show();
                                view.setEnabled(false);
                            }
                            adapter2.notifyDataSetChanged();

                        }
                    }
                });
            break;
            case R.id.userText:
                Toast.makeText(this, "You clicked " + adapter2.getItem(position) + " on row number " + position + view.getId(), Toast.LENGTH_SHORT).show();
                break;
            default: // for item
                Toast.makeText(this, "You clicked " + adapter2.getItem(position) + " on row number " + position + view.getId(), Toast.LENGTH_SHORT).show();
                break;
        }



    }
}