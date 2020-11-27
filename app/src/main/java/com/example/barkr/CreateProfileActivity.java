package com.example.barkr;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.R.layout;
import android.widget.Spinner;
import java.lang.Object;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CreateProfileActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText editHumanName, editHumanDateOfBirth, editHumanPhone, editHumanEmail, editHumanBio;
    Button finish;
    ImageView profileImage, humanImage, dogImage;
    Spinner stateSpinner, citySpinner;
    DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseUser user;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();

        editHumanName = findViewById(R.id.EditOwnerName);
        editHumanDateOfBirth = findViewById(R.id.EditDateofBirth);
        editHumanPhone = findViewById(R.id.editTextPhone);
        editHumanEmail = findViewById(R.id.EditEmail);
        editHumanBio = findViewById(R.id.editBio);

        finish = findViewById(R.id.buttonFinishProfile);
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
        List<String> cities = new ArrayList<String>();
        cities.add("Tyler");
        cities.add("Lindale");
        ArrayAdapter adapterCities = new ArrayAdapter(this, layout.simple_spinner_item, cities);
        adapterCities.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapterCities);

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
            String phone = editHumanPhone.getText().toString();
            String email = editHumanEmail.getText().toString();
            String bio = editHumanBio.getText().toString();
            //int age = editHumanDateOfBirth;
            //HumanProfile hp = new HumanProfile(name, gender, location, phone, email, bio, 0);
            //ArrayList<DogProfile> dogProfiles = new ArrayList<DogProfile>();
            //DogProfile dp = new DogProfile("Bagel", "Corgi", "m", false, true, "Hello", 1);
            //dogProfiles.add(dp);
           // User newUser = new User(editHumanEmail.getText().toString(), hp, dogProfiles);
           // myRef.child("users").child(user.getUid()).setValue(newUser);
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
