package com.example.barkr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewProfileActivity extends AppCompatActivity implements View.OnClickListener {
    User userValue;
    TextView textHumanName, textHumanGenderAge, textLocation, textContact, textNumberPets, textHumanBio, textDistance,
            textPetName, textPetGenderAge, textPetBreed, textSpayedNeutered, textShotsUpToDate, textPetBio;
    FloatingActionButton messageButton, favoritesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        //initialize human views
        //TODO add distance away textViews
        textHumanName = findViewById(R.id.TextViewUser);
        textHumanGenderAge = findViewById(R.id.UsersGenderAge);
        textLocation = findViewById(R.id.TextDisplaysUsersCityState);
        textContact = findViewById(R.id.TextDisplaysContact);
        textNumberPets = findViewById(R.id.TextdisplaysNumofPets);
        textHumanBio = findViewById(R.id.TextDislaysOwnersBio);

        //initialize dog views
        textPetName = findViewById(R.id.TextdiplaysPetsName);
        textPetGenderAge = findViewById(R.id.TextDisplaysPetsGenderAge);
        textPetBreed = findViewById(R.id.TextDisplaysBreed);
        textSpayedNeutered = findViewById(R.id.TextDisplaysSpayedNeutered);
        textShotsUpToDate = findViewById(R.id.TextDisplaysShotsUpToDate);
        textPetBio = findViewById(R.id.textDisplaysBio);

        messageButton = findViewById(R.id.MessagingButton);
        messageButton.setOnClickListener(this);

        favoritesButton = findViewById(R.id.FavoriteButton);
        favoritesButton.setOnClickListener(this);

        if(getIntent().getExtras() != null)
        {
            userValue = (User) getIntent().getSerializableExtra("USER_PROFILE");
        }

        HumanProfile hp = userValue.getHumanProfile();
        DogProfile dp = userValue.getDogProfiles().get(0);

        System.out.println(dp.getname());
        System.out.println(dp.getgender());
        //set text for human values
        textHumanName.setText(hp.getname());
        if (hp.getgender().equals("f")) {
            textHumanGenderAge.setText("Female, " + hp.getage());
        } else if (hp.getgender().equals("m")) {
            textHumanGenderAge.setText("Male, " + hp.getage());
        }
        textLocation.setText(hp.getlocation());
        textContact.setText(userValue.getEmail() + "\n" + hp.getphoneNumber());
        textNumberPets.setText("1 " + dp.getbreed());
        textHumanBio.setText(hp.getbio());

        //set text for dog values
        textPetName.setText(dp.getname());
        System.out.println(dp.getgender());
        if (dp.getgender().equals("f")) {
            if (dp.getAgeYears() == 0 && dp.getAgeMonths() == 0 && dp.getAgeDays() != 0) {
                System.out.println("Female, " + dp.getAgeDays() + " days");
                textPetGenderAge.setText("Female, " + dp.getAgeDays() + " days");
            } else if (dp.getAgeYears() == 0 && dp.getAgeMonths() != 0) {
                System.out.println("Female, " + dp.getAgeMonths() + " months");
                textPetGenderAge.setText("Female, " + dp.getAgeMonths() + " months");
            } else if (dp.getAgeYears() != 0 && dp.getAgeMonths() != 0 && dp.getAgeDays() != 0) {
                System.out.println("Female, " + dp.getAgeYears() + " years");
                textPetGenderAge.setText("Female, " + dp.getAgeYears() + " years");
            }
        } else if (dp.getgender().equals("m")) {
            if (dp.getAgeYears() == 0 && dp.getAgeMonths() == 0 && dp.getAgeDays() != 0) {
                System.out.println("Male, " + dp.getAgeDays() + " days");
                textPetGenderAge.setText("Male>, " + dp.getAgeDays() + " days");
            } else if (dp.getAgeYears() == 0 && dp.getAgeMonths() != 0 && dp.getAgeDays() != 0) {
                System.out.println("Male, " + dp.getAgeMonths() + " months");
                textPetGenderAge.setText("Male, " + dp.getAgeMonths() + " months");
            } else if (dp.getAgeYears() != 0 && dp.getAgeMonths() != 0 && dp.getAgeDays() != 0) {
                System.out.println("Male, " + dp.getAgeYears() + " years");
                textPetGenderAge.setText("Male, " + dp.getAgeYears() + " years");
            }
        }
        textPetBreed.setText(dp.getbreed());
        if (dp.getShotUpToDate()) {
            textShotsUpToDate.setText("Yes");
        } else {
            textShotsUpToDate.setText("No");
        }
        if (dp.getspayedNeutered()) {
            textSpayedNeutered.setText("Yes");
        } else {
            textSpayedNeutered.setText("No");
        }
        textPetBio.setText(dp.getbio());

    }
    @Override
    public void onClick(View v) {
        if (v == messageButton) {
            Intent i = new Intent(ViewProfileActivity.this, MessagingActivity.class);
            i.putExtra("USER_PROFILE", userValue);
            startActivity(i);
        }
        if (v == favoritesButton) {
            DatabaseReference dRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("favorites");
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("favorites");
                    if (snapshot.exists()) {
                        ArrayList<User> newFavList = new ArrayList<User>();
                        boolean isNew = true;
                        for(DataSnapshot ds : snapshot.getChildren())
                        {
                            User currUser = ds.getValue(User.class);
                            if(!currUser.getUserId().equals(userValue.getUserId()))
                            {
                                newFavList.add(currUser);
                            }
                            else {
                                isNew = false;
                            }
                        }
                        if(isNew) {
                            newFavList.add(userValue);
                        }
                        ref.setValue(newFavList);
                    }
                    else
                    {
                        ArrayList<User> newFavList = new ArrayList<User>();
                        newFavList.add(userValue);
                        ref.setValue(newFavList);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    //TODO
                }
            };
            dRef.addListenerForSingleValueEvent(listener);
        }
    }
}