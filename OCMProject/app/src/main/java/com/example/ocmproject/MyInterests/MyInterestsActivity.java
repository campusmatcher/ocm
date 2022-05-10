//package com.example.ocmproject.pending;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.example.ocmproject.MyInterests.MyInterestsAdapter;
//import com.example.ocmproject.R;
//import com.example.ocmproject.User;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//public class MyInterestsActivity extends AppCompatActivity implements MyInterestsAdapter.ItemClickListener{
//
//    FirebaseAuth auth;
//    DatabaseReference mDatabase;
//    ArrayList<String> list;
//    PendingAdapter adapter;
//    RecyclerView recycleView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pending);
//        //Database
//        auth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        //Create an user list and bind adapter
//        list = new ArrayList<>();
//        adapter = new MyInterestsAdapter(this, list);
//        recycleView = findViewById(R.id.pendingList);
//        recycleView.setLayoutManager(new LinearLayoutManager(this));
//        adapter.setClickListener(this);
//        recycleView.setAdapter(adapter);
//
//        // Populate recyclerview with data
//        mDatabase.child("NewUser").child(auth.getUid()).child("Interests").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
//                for (DataSnapshot snap : snapshot.getChildren()) {
//                    String interest = snap.getValue(String.class);
//                    list.add(interest);
//                    adapter.notifyDataSetChanged();
//
//                }
//            }
//
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("Error in retrieving pending info", error.getMessage());
//            }
//        });
//
//    }
//}