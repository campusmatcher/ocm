package com.example.ocmproject.recycleFiles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ocmproject.R;
import com.example.ocmproject.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecycleActivity extends AppCompatActivity implements RecycleAdapter.ItemClickListener {
    FirebaseAuth auth;
    DatabaseReference mDatabase;
    ArrayList<User> list;
    RecycleAdapter adapter;
    RecyclerView recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        list = new ArrayList<>();
        list.add(new User("a","a","a","a"));
        adapter = new RecycleAdapter(this, list);
        recycleView = findViewById(R.id.recycleTest);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setClickListener(this);
        recycleView.setAdapter(adapter);

    }
    @Override
    public void onItemClick(View view, int position) {
        switch(view.getId()){
            case R.id.addButton:
                Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position + view.getId(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.userText:
                Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position + view.getId(), Toast.LENGTH_SHORT).show();
                break;
            default: // for item
                Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position + view.getId(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
