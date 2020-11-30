package com.example.barkr;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.List;

public class CreateProfileActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText editHumanName, editHumanPhone, editHumanBio, editHumanDateOfBirth, editDogDateOfBirth, editDogName, editDogBio;
    Button finish;
    ImageView profileImage, humanImage, dogImage;
    Spinner stateSpinner, citySpinner, humanGenderSpinner, dogGenderSpinner, dogBreedSpinner;
    DatabaseReference myRef;
    CheckBox spayedNeutered, shotsUpToDate;
    private FirebaseDatabase database;
    private FirebaseUser user;
    DatePickerDialog datePickerDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();

        editHumanName = findViewById(R.id.EditOwnerName);
        editHumanPhone = findViewById(R.id.editTextPhone);
        editHumanBio = findViewById(R.id.editBio);
        editDogName = findViewById(R.id.editTextPetName);
        editDogBio = findViewById(R.id.editTextPetBio);

        editHumanDateOfBirth = findViewById(R.id.EditDateofBirth);
        editHumanDateOfBirth.setOnClickListener(this);
        editDogDateOfBirth = findViewById(R.id.editDogDateofBirth);
        editDogDateOfBirth.setOnClickListener(this);

        spayedNeutered = findViewById(R.id.checkBoxSpayed_Neutered);
        shotsUpToDate = findViewById(R.id.checkBoxShotsUptodate);

        finish = findViewById(R.id.buttonFinishProfile);
        finish.setOnClickListener(this);

        //initialize and set values for the state spinner and its adapter
        //this is currently using sample values, will come back and put api values later
        stateSpinner = findViewById(R.id.Spinner_State);
        stateSpinner.setOnItemSelectedListener(this);
        List<String> states = new ArrayList<String>();
        states.add("");
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
        cities.add("");
        cities.add("Tyler");
        cities.add("Lindale");
        ArrayAdapter adapterCities = new ArrayAdapter(this, layout.simple_spinner_item, cities);
        adapterCities.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapterCities);

        humanGenderSpinner = findViewById(R.id.spinnerHumanGender);
        humanGenderSpinner.setOnItemSelectedListener(this);
        List<String> genders = new ArrayList<String>();
        genders.add("");
        genders.add("Female");
        genders.add("Male");
        ArrayAdapter adapterHumanGender = new ArrayAdapter(this, layout.simple_spinner_item, genders);
        adapterHumanGender.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        humanGenderSpinner.setAdapter(adapterHumanGender);

        dogGenderSpinner = findViewById(R.id.spinnerGender);
        humanGenderSpinner.setOnItemSelectedListener(this);
        ArrayAdapter adapterDogGender = new ArrayAdapter(this, layout.simple_spinner_item, genders);
        adapterDogGender.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        dogGenderSpinner.setAdapter(adapterDogGender);

        dogBreedSpinner = findViewById(R.id.spinnerBreed);
        dogBreedSpinner.setOnItemSelectedListener(this);
        List<String> breeds = new ArrayList<String>();
        breeds.add("");
        breeds.add("corgi");
        breeds.add("beagle");
        ArrayAdapter adapterDogBreeds = new ArrayAdapter(this, layout.simple_spinner_item, breeds);
        adapterDogBreeds.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        dogBreedSpinner.setAdapter(adapterDogBreeds);
    }

    @Override
    public void onClick(View v) {
        if(v == finish)
        {
            //rewrite value to the database
            //this is not checking if any values are empty or correct values! Will be changing soon//TODO
            String name = editHumanName.getText().toString();
            String gender = "";
            if(humanGenderSpinner.getSelectedItem().toString().equals("Female"))
            {
                gender = "f";
            }
            else if(humanGenderSpinner.getSelectedItem().toString().equals("Male"))
            {
                gender = "m";
            }
            String location = citySpinner.getSelectedItem().toString() + ", " + stateSpinner.getSelectedItem().toString();
            String phone = editHumanPhone.getText().toString();
            String bio = editHumanBio.getText().toString();
            String birthdate = editHumanDateOfBirth.getText().toString();
            String email = user.getEmail();
            HumanProfile hp = new HumanProfile(name, gender, location, phone, bio, birthdate);
            ArrayList<DogProfile> dogProfiles = new ArrayList<DogProfile>();
            String dogName = editDogName.getText().toString();
            String dogBreed = dogBreedSpinner.getSelectedItem().toString();
            String dogGender = "";
            if(dogGenderSpinner.getSelectedItem().toString().equals("Female"))
            {
                dogGender = "f";
            }
            else if(dogGenderSpinner.getSelectedItem().toString().equals("Male"))
            {
                dogGender = "m";
            }
            boolean spayNeuter = false;
            boolean shotsUpDate = false;
            if(spayedNeutered.isChecked() == true)
            {
                spayNeuter = true;
            }
            if(shotsUpToDate.isChecked() == true)
            {
                shotsUpDate = true;
            }
            String dogBio = editDogBio.getText().toString();
            String dogBirthdate = editDogDateOfBirth.getText().toString();
            DogProfile dp  = new DogProfile(dogName, dogBreed, dogGender, spayNeuter, shotsUpDate, dogBio, dogBirthdate);
            dogProfiles.add(dp);
            User newUser = new User(email, hp, dogProfiles, user.getUid());
           myRef.child("users").child(user.getUid()).setValue(newUser);

            startActivity(new Intent(CreateProfileActivity.this, MainActivity.class));
        }
        if(v == editHumanDateOfBirth)
        {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            datePickerDialog = new DatePickerDialog(CreateProfileActivity.this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // set day of month , month and year value in the edit text
                            editHumanDateOfBirth.setText(dayOfMonth + "/"
                                    + (monthOfYear + 1) + "/" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if(v == editDogDateOfBirth)
        {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            datePickerDialog = new DatePickerDialog(CreateProfileActivity.this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // set day of month , month and year value in the edit text
                            editDogDateOfBirth.setText(dayOfMonth + "/"
                                    + (monthOfYear + 1) + "/" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
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
        if(view == humanGenderSpinner) {

        }
        if(view == dogGenderSpinner) {

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
