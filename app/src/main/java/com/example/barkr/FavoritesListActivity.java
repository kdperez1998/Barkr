package com.example.barkr;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoritesListActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView resultsRecycler;
    ArrayList<User> userList;
    static FavoritesListAdapter adapter;
    ImageButton backButton;
    FirebaseUser user;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_list);

        user = FirebaseAuth.getInstance().getCurrentUser();

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        /*HumanProfile hp = new HumanProfile("name", "f", "Tyler, Texas", "903372", "hello", "12/12/1222");
        DogProfile dogProfile = new DogProfile("pet", "corgi", "f", false, false, "hello", "12/12/1212");
        ArrayList<DogProfile> dp = new ArrayList<DogProfile>();
        dp.add(dogProfile);
        userList.add(new User("email", hp, dp, "userId"));
         */

        //get favorites list from the database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList = new ArrayList<User>();
                if (snapshot.exists()) {
                    User currentUser = snapshot.getValue(User.class);
                    for(DataSnapshot dataSnapshot : snapshot.child("favorites").getChildren())
                    {
                        if(dataSnapshot.getValue(User.class) != null) {
                            userList.add(dataSnapshot.getValue(User.class));
                        }
                    }

                    //set extra value to be shown in the adapter
                    resultsRecycler = findViewById(R.id.RecyclerViewFavoritesList);
                    adapter = new FavoritesListAdapter(FavoritesListActivity.this, userList);
                    resultsRecycler.setAdapter(adapter);
                    resultsRecycler.setLayoutManager(new LinearLayoutManager(FavoritesListActivity.this));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addListenerForSingleValueEvent(listener);

    }





    //on click for buttons outside of recycler view
    @Override
    public void onClick(View v)
    {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
