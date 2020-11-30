package com.example.barkr;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public  class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView navView;
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
                        //load messaging fragment TODO
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
        }
        return loadFragment(fragment);
    }
}