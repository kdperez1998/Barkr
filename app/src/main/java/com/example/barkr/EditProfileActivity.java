package com.example.barkr;

import android.R.layout;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener
{
    EditText editHumanName, editHumanDateOfBirth, editPhoneNumber, editHumanBio, editDogDateOfBirth, editDogName, editDogBio;
    ImageView profileImage, profilePicture;
    Button finish;
    Spinner stateSpinner, citySpinner, humanGenderSpinner, dogGenderSpinner, dogBreedSpinner;
    CheckBox spayedNeutered, shotsUpToDate;
    DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    List<String> cities = new ArrayList<String>();
    List<String> states = new ArrayList<String>();
    List<String> genders = new ArrayList<String>();
    List<String> breeds = new ArrayList<String>();
    DatePickerDialog datePickerDialog;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        user = FirebaseAuth.getInstance().getCurrentUser();

        editHumanName = findViewById(R.id.EditOwnerName);
        editPhoneNumber = findViewById(R.id.editTextPhone);
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

        humanGenderSpinner = findViewById(R.id.spinnerHumanGender);
        humanGenderSpinner.setOnItemSelectedListener(this);
        genders = new ArrayList<String>();
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
        breeds = new ArrayList<String>();
        breeds.add("");
        breeds.add("corgi");
        breeds.add("beagle");
        ArrayAdapter adapterDogBreeds = new ArrayAdapter(this, layout.simple_spinner_item, breeds);
        adapterDogBreeds.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        dogBreedSpinner.setAdapter(adapterDogBreeds);
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
                    String humanGender = userValue.getHumanProfile().getgender();
                    //find where the spinner will need to be selected to pre load the value and load the value
                    //cities
                    int indexCities = 0;
                    for(int i = 0; i < cities.size(); i++)
                    {
                        if(cities.get(i).equals(city))
                        {
                            indexCities = i;
                        }
                    }
                    citySpinner.setSelection(indexCities);
                    int indexStates = 0;
                    //states
                    for(int i = 0; i < states.size(); i++)
                    {
                        if(states.get(i).equals(state))
                        {
                            indexStates = i;
                        }
                    }
                    //gender for human
                    int indexHumanGender = 0;
                    for(int i = 0; i < genders.size(); i++)
                    {
                        if(genders.get(i).equals(humanGender))
                        {
                            indexHumanGender = i;
                        }
                    }
                    humanGenderSpinner.setSelection(indexHumanGender);

                    editHumanName.setText(userValue.getHumanProfile().getname());
                    editHumanBio.setText(userValue.getHumanProfile().getbio());
                    editPhoneNumber.setText(userValue.getHumanProfile().getphoneNumber());
                    editHumanDateOfBirth.setText(userValue.getHumanProfile().getDOB());
                    //set dog values at begining of screen open
                    editDogDateOfBirth.setText(userValue.getDogProfiles().get(0).getDOB());
                    editDogName.setText(userValue.getDogProfiles().get(0).getname());
                    editDogBio.setText(userValue.getDogProfiles().get(0).getbio());
                    String dogGender = userValue.getDogProfiles().get(0).getgender();
                    String dogBreed = userValue.getDogProfiles().get(0).getbreed();
                    //gender for dog
                    int indexDogGender = 0;
                    for(int i = 0; i < genders.size(); i++)
                    {
                        if(genders.get(i).equals(dogGender))
                        {
                            indexDogGender = i;
                        }
                    }
                    dogGenderSpinner.setSelection(indexDogGender);
                    //Dog breed
                    int indexDogBreed = 0;
                    for(int i = 0; i < breeds.size(); i++)
                    {
                        if(breeds.get(i).equals(dogBreed))
                        {
                            indexDogBreed = i;
                        }
                    }
                    dogBreedSpinner.setSelection(indexDogBreed);
                    if(userValue.getDogProfiles().get(0).getShotUpToDate())
                    {
                        shotsUpToDate.setChecked(true);
                    }
                    if(userValue.getDogProfiles().get(0).getspayedNeutered())
                    {
                        spayedNeutered.setChecked(true);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("EditProfileActivity", error.getMessage());
            }
        };
        userRef.addListenerForSingleValueEvent(listener);

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
            String phone = editPhoneNumber.getText().toString();
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

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("FRAGMENT_TO_LOAD", 3);
            startActivity(intent);
        }
        if(v == editHumanDateOfBirth)
        {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR); // current year
            int mMonth = c.get(Calendar.MONTH); // current month
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            // date picker dialog
            datePickerDialog = new DatePickerDialog(EditProfileActivity.this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // set day of month , month and year value in the edit text
                            editHumanDateOfBirth.setText((monthOfYear + 1) + "/"
                                    + dayOfMonth + "/" + year);

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
            datePickerDialog = new DatePickerDialog(EditProfileActivity.this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // set day of month , month and year value in the edit text
                            editDogDateOfBirth.setText((monthOfYear + 1) + "/"
                                    + dayOfMonth + "/" + year);

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