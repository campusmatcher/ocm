package com.example.ocmproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.ocmproject.Fragments.ActivityFragment;
import com.example.ocmproject.Fragments.ConnectionsFragment;
import com.example.ocmproject.Fragments.HomeFragment;
import com.example.ocmproject.Fragments.InterestsFragment;
import com.example.ocmproject.Fragments.Matchv2Fragment;
import com.example.ocmproject.Fragments.NotificationsFragment;
import com.example.ocmproject.Fragments.OtherProfileFragment;
import com.example.ocmproject.Fragments.ProfileFragment;
import com.example.ocmproject.Fragments.Settingsv2Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    // Yavuz was here
    // Sencer was here
    // bilal was here
    // Arda was here
    //again

    private ImageView profileButton;
    ImageView settingButton;
    BottomNavigationView bottomNavigationView;

    Matchv2Fragment matchv2Fragment = new Matchv2Fragment();
    ConnectionsFragment connectionsFragment = new ConnectionsFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    NotificationsFragment notificationsFragment = new NotificationsFragment();
    HomeFragment homeFragment = new HomeFragment();
    ActivityFragment activityFragment = new ActivityFragment();
    InterestsFragment interestsFragment = new InterestsFragment();
    OtherProfileFragment otherProfileFragment = new OtherProfileFragment();
    Settingsv2Fragment settingsv2Fragment = new Settingsv2Fragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        profileButton = findViewById(R.id.profile_button);
        settingButton = findViewById(R.id.sandwich_button);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
            }
        });
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container,settingsv2Fragment).commit();
            }
        });


        bottomNavigationView = findViewById(R.id.bottom_nav_bar);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.match:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, matchv2Fragment).commit();
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
