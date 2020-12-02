package com.example.barkr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public  class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView navView;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //no need to check if null since this gives 0 when no extra exists
        if(getIntent().getExtras() != null) {
            int intentFragment = getIntent().getExtras().getInt("FRAGMENT_TO_LOAD");
            //instead check if it is equal to 0
            if (intentFragment != 0) {
                switch (intentFragment) {
                    case 1:
                        //load search main fragment
                        loadFragment(new SearchActivityMain());
                        break;
                    case 2:
                        //load messaging fragment
                        loadFragment(new MessagingListFragment());
                        break;
                    case 3:
                        //load view profile fragment
                        loadFragment(new ViewProfileMain());
                        break;
                }
            }

            //load default fragment if intentFragment equals 0
            else if (intentFragment == 0) {
                loadFragment(new SearchActivityMain());
            }
        }
        else
        {
            //load default fragment
            loadFragment(new SearchActivityMain());
        }
        //get bottom nav view and attach to listener
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        //check if user is signed in
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //check if their profile is created in the database, if not, sent to the create activity page
            FirebaseUser user = mAuth.getCurrentUser();
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference userRef = rootRef.child("users").child(user.getUid());
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        //send the user to the create profile screen so they may input their profile information
                        startActivity(new Intent(MainActivity.this, CreateProfileActivity.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("EmailPassword", error.getMessage());
                }
            };
            userRef.addListenerForSingleValueEvent(listener);

        }
        else
        {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

    }
        private boolean loadFragment(Fragment frag) {
        //switches fragment
        if(frag != null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frag).commit();
            return true;
        }
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch(item.getItemId()) {
            case R.id.navigation_search:
                fragment = new SearchActivityMain();
                break;
            case R.id.navigation_profile:
                fragment = new ViewProfileMain();
                break;
            case R.id.navigation_messaging:
                fragment = new MessagingListFragment();
                break;
            }
        return loadFragment(fragment);
    }
}