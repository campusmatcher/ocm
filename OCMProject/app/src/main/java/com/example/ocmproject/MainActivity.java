package com.example.ocmproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ocmproject.Fragments.ActivityFragment;
import com.example.ocmproject.Fragments.ConnectionsFragment;
import com.example.ocmproject.Fragments.HomeFragment;
import com.example.ocmproject.Fragments.InterestsFragment;
import com.example.ocmproject.Fragments.MatchFragment;
import com.example.ocmproject.Fragments.Matchv2Fragment;
import com.example.ocmproject.Fragments.NotificationsFragment;
import com.example.ocmproject.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
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
    //again

    private ImageView profileButton;
    BottomNavigationView bottomNavigationView;

    Matchv2Fragment matchFragment = new Matchv2Fragment();
    ConnectionsFragment connectionsFragment = new ConnectionsFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    NotificationsFragment notificationsFragment = new NotificationsFragment();
    HomeFragment homeFragment = new HomeFragment();
    ActivityFragment activityFragment = new ActivityFragment();
    InterestsFragment interestsFragment = new InterestsFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        profileButton = findViewById(R.id.profile_button);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, matchFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.match:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, matchFragment).commit();
                        return true;
                    case R.id.connections:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, connectionsFragment).commit();
                        return true;
                    case R.id.notifs:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, notificationsFragment).commit();
                        return true;
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;
                    case R.id.activity:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, activityFragment).commit();
                        return true;
                }

                return false;
            }
        });


    }
}
