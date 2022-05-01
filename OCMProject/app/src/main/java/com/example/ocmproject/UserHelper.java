//package com.example.ocmproject;
//
//import android.widget.TextView;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class UserHelper {
//    private FirebaseAuth auth;
//    private DatabaseReference mRootRef;
//    private DatabaseReference mDatabase;
//    private String userId;
//    private String name;
//    private String surname;
//    private String email;
//    private TextView profileName;
//    private TextView profileSurname;
//    private TextView profileEmail;
//    public UserHelper(){
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        auth = FirebaseAuth.getInstance();
//        mRootRef = FirebaseDatabase.getInstance().getReference();
//        profileName = findViewById(R.id.profileName);
//        profileSurname = findViewById(R.id.profileSurname);
//        profileEmail = findViewById(R.id.profileEmail);
//        userId = auth.getCurrentUser().getUid();
//        User user =
//
//    }
//}
