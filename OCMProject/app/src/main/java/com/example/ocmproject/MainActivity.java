package com.example.ocmproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    // Yavuz was here
    // Sencer was here
    // bilal was here
    // Arda was here
    // l12345
    //again

    private Button logout;
    private EditText edit;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        logout = findViewById(R.id.logout);
        edit = findViewById(R.id.edit);
        add = findViewById(R.id.add);

        // Logout button's function
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
                finish();
            }
        });

//         Adding one value
//         FirebaseDatabase.getInstance().getReference().child("ocmdatabase").child("Android").setValue("abcd");

//        HashMap<String , Object> map = new HashMap<>();
//        map.put("Name", "Alp");
//        map.put("Email", "sencer@ocm.com");
//
//        FirebaseDatabase.getInstance().getReference().child("Isimler").child("CokluDeger").updateChildren(map);

        // Add button's functions
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //Adding eventListener to reference
//                //Creating and initializing User class object
//                Abc abc2 = new Abc("user1234", "abc", "xyz", "First", "January", "2001");
//
//                //Getting Instance of Firebase realtime database
//                FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();
//
//                //Getting Reference to a User node, (it will be created if not already there)
//                DatabaseReference userNode = database.getReference("Abc2");
//
//                //Writing the User class object to that reference
//                userNode.setValue(abc2);


//                ArrayList<String> liste = new ArrayList<String>();
//                liste.add("kelime 1");
//                liste.add("kelime 2");
//                liste.add("kelime 3");
//                liste.add("kelime 4");

                for(int i = 0; i < 40; i++){
                    FirebaseDatabase.getInstance().getReference().child("Users").child("hZ86xa3nw1PO8dUmBIW0UTk28R53").child("Schedule").setValue("0");
                }



                myRef.child("Abc").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Getting the string value of that node
                        //Abc abc2 =  dataSnapshot.getValue(Abc.class);
                        //Toast.makeText(MainActivity.this, "Data Received: username: " + abc2.getFirstName(), Toast.LENGTH_SHORT).show();
                        //Log.e("OK", "BASARILI" + abc2.getLastName() );



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Error", "onCancelled: Something went wrong! Error:" + databaseError.getMessage() );

                    }
                });




//                String txt_name = edit.getText().toString();
////                ArrayList<String> liste = new ArrayList<String>();
////                liste.add("Deneme 1");
//                if(txt_name.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "No Name Entered!", Toast.LENGTH_SHORT).show();
//                } else {
//                    FirebaseDatabase.getInstance().getReference().child("Courses").child("Math102-01").child("day1").child("les1").setValue("0");
//                    FirebaseDatabase.getInstance().getReference().child("Courses").child("Eng102-01").child("day1").child("les1").setValue("0");
//                    FirebaseDatabase.getInstance().getReference().child("Courses").child("Cs102-01").child("day1").child("les1").setValue("0");
//                    FirebaseDatabase.getInstance().getReference().child("Courses").child("Turk102-01").child("day1").child("les1").setValue("0");
//                    FirebaseDatabase.getInstance().getReference().child("Courses").child("Math132-01").child("day1").child("les1").setValue("0");
//
////                    FirebaseDatabase.getInstance().getReference().child("Users").child("hZ86xa3nw1PO8dUmBIW0UTk28R53").child("Schedule").child("day2").child("les2").setValue("0");
////                    FirebaseDatabase.getInstance().getReference().child("Users").child("hZ86xa3nw1PO8dUmBIW0UTk28R53").child("Schedule").child("day3").child("les3").setValue("0");
////                    FirebaseDatabase.getInstance().getReference().child("Users").child("hZ86xa3nw1PO8dUmBIW0UTk28R53").child("Schedule").child("day4").child("les4").setValue("1");
////                    FirebaseDatabase.getInstance().getReference().child("Users").child("hZ86xa3nw1PO8dUmBIW0UTk28R53").child("Schedule").child("day5").child("les5").setValue("1");
////                    FirebaseDatabase.getInstance().getReference().child("Users").child("hZ86xa3nw1PO8dUmBIW0UTk28R53").child("Schedule").child("day6").child("les6").setValue("0");
////                    FirebaseDatabase.getInstance().getReference().child("Users").child("hZ86xa3nw1PO8dUmBIW0UTk28R53").child("Schedule").child("day7").child("les7").setValue("1");
//                    Toast.makeText(MainActivity.this, "Addition completed!", Toast.LENGTH_SHORT).show();
//                    //startActivity(new Intent(MainActivity.this, ScheduleActivity.class));
            }

        });
    }
}
