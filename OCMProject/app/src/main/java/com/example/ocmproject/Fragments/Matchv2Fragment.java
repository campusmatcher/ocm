package com.example.ocmproject.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ocmproject.Matcher;
import com.example.ocmproject.R;
import com.example.ocmproject.User;
import com.example.ocmproject.UserListAdapter;
import com.example.ocmproject.match.MatchAdapterv2;
import com.example.ocmproject.match.Matcherv2;
import com.example.ocmproject.match.Matchv2Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Matchv2Fragment extends Fragment implements MatchAdapterv2.ItemClickListener{
    FirebaseAuth auth;
    DatabaseReference mDatabase;
    ArrayList<User> list;
    MatchAdapterv2 adapter2;
    RecyclerView recycleView;
    Matcherv2 matcher2;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_match, container, false);

        //Database
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Create matcher object and update matchlist
        matcher2 = new Matcherv2();
        matcher2.findAndUpdateMatchList();

        //Create an user list and bind adapter
        list = new ArrayList<>();
        adapter2 = new MatchAdapterv2(getActivity(), list);
        recycleView = view.findViewById(R.id.matchRecycler);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
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
        return view;
    }

    //method defining click action
    @Override
    public void onItemClick(View view, int position) {
        switch(view.getId()){
            case R.id.addButton:
                Toast.makeText(getActivity(), "You clicked " + adapter2.getItem(position) + " on row number " + position + view.getId(), Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(getActivity(), "a", Toast.LENGTH_SHORT).show();//??
                                view.setEnabled(false);
                            }
                            else{
                                current.addItemToSent(adapter2.getItem(position).getId());
                                Toast.makeText(getActivity(), "b", Toast.LENGTH_SHORT).show();//??
                                view.setEnabled(false);
                            }
                            adapter2.notifyDataSetChanged();

                        }
                    }
                });
                break;
            case R.id.userText:
                Toast.makeText(getActivity(), "You clicked " + adapter2.getItem(position) + " on row number " + position + view.getId(), Toast.LENGTH_SHORT).show();
                break;
            default: // for item
                Toast.makeText(getActivity(), "You clicked " + adapter2.getItem(position) + " on row number " + position + view.getId(), Toast.LENGTH_SHORT).show();
                break;
        }



        ////---------------------------

//        Matcher matcher = new Matcher();
//        matcher.findAndUpdateMatchList();
//
//        auth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        userId = auth.getCurrentUser().getUid();
//        list = new ArrayList<>();
//        adapter = new UserListAdapter(getActivity(), list);
//        ListView matchList = view.findViewById(R.id.matchList);
//
//        matchList.setAdapter(adapter);
//
//        mDatabase.child("NewUser").child(userId).child("MatchList").addValueEventListener(new ValueEventListener() {
//            //mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
//                for (DataSnapshot snap : snapshot.getChildren()) {
//                    String friendId = snap.getValue(String.class);
//                    //Iterable<Object> = mDatabase.child("Users").child(userId).child("Contacts").get
//                    DatabaseReference friendRefs = FirebaseDatabase.getInstance().getReference().child("NewUser").child(friendId);
//                    friendRefs.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapsho) {
//                            User friendUserObj = snapsho.getValue(User.class);
//                            list.add(friendUserObj);
//                            adapter.notifyDataSetChanged();
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            }
//
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//


    }

}