package com.example.barkr;

import android.R.layout;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener
{
    EditText editHumanName, editHumanDateOfBirth, editEmail, editPhoneNumber, editHumanBio;
    ImageView profileImage, profilePicture;
    Button finish;
    Spinner stateSpinner, citySpinner;
    DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    List<String> cities = new ArrayList<String>();
    List<String> states = new ArrayList<String>();

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();

        editHumanName = findViewById(R.id.EditOwnerName);
        editHumanDateOfBirth = findViewById(R.id.EditDateofBirth);
        editEmail = findViewById(R.id.EditEmail);
        editPhoneNumber = findViewById(R.id.editTextPhone);
        editHumanBio = findViewById(R.id.editBio);

        finish = findViewById(R.id.button);
        finish.setOnClickListener(this);

        //initialize and set values for the state spinner and its adapter
        //this is currently using sample values, will come back and put api values later
        stateSpinner = findViewById(R.id.Spinner_State);
        stateSpinner.setOnItemSelectedListener(this);
        List<String> states = new ArrayList<String>();
        states.add("Texas");
        states.add("Oklahoma");
        ArrayAdapter adapterStates = new ArrayAdapter(this, layout.simple_spinner_item, states);
        adapterStates.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapterStates);

        //initialize and set values for the city spinner and its adapter
        //this is currently using sample values, will come back and put api values later
        citySpinner = findViewById(R.id.spinner_City);
        citySpinner.setOnItemSelectedListener(this);
        cities.add("Tyler");
        cities.add("Lindale");
        ArrayAdapter adapterCities = new ArrayAdapter(this, layout.simple_spinner_item, cities);
        adapterCities.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapterCities);

    }

    @Override
    public void onStart()
    {
        super.onStart();
        //check if user has values linked to it already

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child("users").child(user.getUid());
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists())
                {
                    startActivity(new Intent(EditProfileActivity.this, CreateProfileActivity.class));
                }
                else
                {
                    User userValue = snapshot.getValue(User.class);
                    //parse the location string into two values
                    String[] location = userValue.getHumanProfile().getlocation().split(", ");
                    String city = location[0];
                    String state = location[1];
                    //find where the spinner will need to be selected to pre load the value and load the value
                    //cities
                    int indexCities = 0;
                    for(int i = 0; i < cities.size(); i++)
                    {
                        if(cities.get(i) == city)
                        {
                            indexCities = i;
                        }
                    }
                    citySpinner.setSelection(indexCities);
                    int indexStates = 0;
                    //states
                    for(int i = 0; i < states.size(); i++)
                    {
                        if(states.get(i) == city)
                        {
                            indexStates = i;
                        }
                    }
                    stateSpinner.setSelection(indexStates);

                    editHumanName.setText(userValue.getHumanProfile().getname());
                    editHumanBio.setText(userValue.getHumanProfile().getbio());
                    editEmail.setText(userValue.getHumanProfile().getemail());
                    editPhoneNumber.setText(userValue.getHumanProfile().getphoneNumber());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("EmailPassword", error.getMessage());
            }
        };
        userRef.addListenerForSingleValueEvent(listener);

    }

    @Override
    public void onClick(View v) {
        if(v == finish)
        {
            //rewrite value to the database
            //this is not checking if any values are empty or correct values! Will be changing soon
            //also I am using sample values for the dog profile right now. Will fix when I implement dog profile editing
            String name = editHumanName.getText().toString();
            String gender = "none given";
            String location = citySpinner.getSelectedItem().toString() + ", " + stateSpinner.getSelectedItem().toString();
            String phone = editPhoneNumber.getText().toString();
            String email = editPhoneNumber.getText().toString();
            String bio = editHumanBio.getText().toString();
            //int age = editHumanDateOfBirth;
            HumanProfile hp = new HumanProfile(name, gender, location, phone, email, bio, 0);
            ArrayList<DogProfile> dogProfiles = new ArrayList<DogProfile>();
            DogProfile dp = new DogProfile("Bagel", "Corgi", "m", false, true, "Hello", 1);
            dogProfiles.add(dp);
            User newUser = new User(editEmail.getText().toString(), hp, dogProfiles);
            myRef.child("users").child(user.getUid()).setValue(newUser);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        //an item is selected. Retrieve it by using parent.getItemPosition(pos)
        if(view == citySpinner) {
            //citySpinner.setSelection(pos);
            //city = parent.getItemAtPosition(pos);
        }
        if(view == stateSpinner) {
            //stateSpinner.setSelection(pos);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}