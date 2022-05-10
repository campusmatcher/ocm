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
import android.widget.Toast;

import com.example.ocmproject.R;
import com.example.ocmproject.User;
import com.example.ocmproject.pending.PendingAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class NotificationsFragment extends Fragment implements PendingAdapter.ItemClickListener {
    FirebaseAuth auth;
    DatabaseReference mDatabase;
    ArrayList<User> list;
    PendingAdapter adapter;
    RecyclerView recycleView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_notifications, container, false);

        //Database
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Create an user list and bind adapter
        list = new ArrayList<>();
        adapter = new PendingAdapter(getActivity(), list);
        recycleView = view.findViewById(R.id.pendingList);
        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setClickListener(this);
        recycleView.setAdapter(adapter);

        // Populate recyclerview with data
        mDatabase.child("NewUser").child(auth.getUid()).child("Pending").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String pendingId = snap.getValue(String.class);
                    DatabaseReference friendRefs = FirebaseDatabase.getInstance().getReference().child("NewUser").child(pendingId);
                    friendRefs.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()){
                                ///
                            }
                            else {
                                DataSnapshot snapsho = task.getResult();
                                User friendUserObj = snapsho.getValue(User.class);
                                list.add(friendUserObj);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error in retrieving pending info", error.getMessage());
            }
        });

        return view;
    }

    //method defining click action
    @Override
    public void onItemClick(View view, int position) {
        switch(view.getId()){
            case R.id.acceptButton:
                Toast.makeText(getActivity(), "You clicked " + adapter.getItem(position) + " on row number " + position + view.getId(), Toast.LENGTH_SHORT).show();
                mDatabase.child("NewUser").child(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(!task.isSuccessful()){
                            ///
                        }
                        else{
                            User current = task.getResult().getValue(User.class);
                            current.acceptContact(adapter.getItem(position).getId());
                            Toast.makeText(getActivity(), "c", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();

                        }
                    }
                });
                break;
            case R.id.rejectPending:
                Toast.makeText(getActivity(), "You clicked " + adapter.getItem(position) + " on row number " + position + view.getId(), Toast.LENGTH_SHORT).show();
                mDatabase.child("NewUser").child(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(!task.isSuccessful()){
                            Log.e("Error in retrieving user object for matching", task.toString());
                        }
                        else{
                            User current = task.getResult().getValue(User.class);
                            current.deleteFromPending(adapter.getItem(position).getId());
                            Toast.makeText(getActivity(), "d", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();

                        }
                    }
                });
                break;

            case R.id.userText:
                Toast.makeText(getActivity(), "You clicked " + adapter.getItem(position) + " on row number " + position + view.getId(), Toast.LENGTH_SHORT).show();
                break;
            default: // for item
                Toast.makeText(getActivity(), "You clicked " + adapter.getItem(position) + " on row number " + position + view.getId(), Toast.LENGTH_SHORT).show();
                break;
        }

    }
}