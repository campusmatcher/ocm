package com.example.ocmproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserListAdapter extends ArrayAdapter<User> {
    private Context context;
    private ArrayList<User> list;
    private LayoutInflater inflater;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private String userId;


    public UserListAdapter(Context context, ArrayList<User> list){
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public User getItem(int pos) {
        return list.get(pos);
    }

    @Override
    //https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView
    //https://stackoverflow.com/questions/40862154/how-to-create-listview-items-button-in-each-row
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        User other = getItem(position);


        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.user_list_item, parent, false); //LayoutInflater.from(getContext())
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.userText);



        //TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        // Populate the data into the template view using the data object
        tvName.setText(other.getName() + " " + other.getSurname());
        //tvHome.setText(user.getSurname());

        Button addButton = (Button) convertView.findViewById(R.id.addButton);


        if (other.getSent() != null && other.getSent().values().contains(auth.getUid())){
            addButton.setText("ACCEPT REQUEST");
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabase.child("NewUser").child(auth.getUid()).child("Contacts").child(other.getId()).setValue(other.getId());
                    mDatabase.child("NewUser").child(auth.getUid()).child("Pending").child(other.getId()).removeValue();
                    mDatabase.child("NewUser").child(other.getId()).child("Sent").child(auth.getUid()).removeValue();
                    mDatabase.child("NewUser").child(other.getId()).child("Contacts").child(auth.getUid()).setValue(auth.getUid());
                    list.remove(other);
                    UserListAdapter.this.notifyDataSetChanged();

                }

            });
        }
        // if the user already sent connection request, make the button disable
        else if (other.getPending()!= null && other.getPending().values().contains(auth.getUid())){
            addButton.setEnabled(false);
        }
        else {
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabase.child("NewUser").child(auth.getUid()).child("Sent").child(other.getId()).setValue(other.getId());
                    //mDatabase.child("NewUser").child(auth.getUid()).child("MatchList").child(other.getId()).removeValue();
                    mDatabase.child("NewUser").child(other.getId()).child("Pending").child(auth.getUid()).setValue(auth.getUid());
                    list.remove(other);
                    UserListAdapter.this.notifyDataSetChanged();


                }

            });
        }
        // Return the completed view to render on screen
        return convertView;
    }

}
