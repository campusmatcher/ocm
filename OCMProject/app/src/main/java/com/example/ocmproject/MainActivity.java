package com.example.ocmproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    // Yavuz was here
    // Sencer was here
    // bilal was here
    private Button logout;
    private EditText edit;
    private Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                String txt_name = edit.getText().toString();
                if(txt_name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No Name Entered!", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase.getInstance().getReference().child("OCM VERILERI").push().child("Name").setValue(txt_name);
                    startActivity(new Intent(MainActivity.this, ScheduleActivity.class));

                }
            }
        });
    }
}
