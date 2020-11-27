package com.example.barkr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

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

public class AdvancedSearchActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener
{

    EditText dogAgeNum, humanAgeNum;
    CheckBox dogMale, dogFemale, humanMale, humanFemale, spayedNeutered, shotsUpToDate;
    Switch saveFilters;
    Spinner dogAgeDescription, breed;
    Button search;
    FirebaseUser user;
    ArrayList<String> breeds, dogAgeDescriptionValues;
    Filter f;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);

        breeds = new ArrayList<String>();
        breeds.add("corgi");
        breeds.add("beagle");

        dogAgeDescriptionValues = new ArrayList<String>();
        dogAgeDescriptionValues.add("months");
        dogAgeDescriptionValues.add("years");
        dogAgeDescriptionValues.add("days");
        //initialize variables and set listeners
        dogAgeNum = findViewById(R.id.editTextDogAge);
        humanAgeNum = findViewById(R.id.editTextHumanAge);

        dogMale = findViewById(R.id.checkBoxMaleDog);
        dogFemale = findViewById(R.id.checkBoxFemaleDog);
        humanMale = findViewById(R.id.checkBoxMaleHuman);
        humanFemale = findViewById(R.id.checkBoxFemaleHuman);
        spayedNeutered = findViewById(R.id.checkBoxSpayedNeutered);
        shotsUpToDate = findViewById(R.id.checkBoxShotsUpToDate);

        saveFilters = findViewById(R.id.filterSwitch);

        dogAgeDescription = findViewById(R.id.SpinnerAgeDog);
        dogAgeDescription.setOnItemSelectedListener(this);
        ArrayAdapter adapterDogAge = new ArrayAdapter(this, android.R.layout.simple_spinner_item, dogAgeDescriptionValues);
        adapterDogAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dogAgeDescription.setAdapter(adapterDogAge);

        breed = findViewById(R.id.spinnerBreed);
        breed.setOnItemSelectedListener(this);
        ArrayAdapter adapterBreed = new ArrayAdapter(this, android.R.layout.simple_spinner_item, breeds);
        adapterBreed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breed.setAdapter(adapterBreed);

        search = findViewById(R.id.searchButton);
        search.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void onStart() {
        super.onStart();
        //check if user has values saved previously

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference filterRef = rootRef.child("users").child(user.getUid()).child("filters");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Filter filterValue = snapshot.getValue(Filter.class);

                    dogAgeNum.setText(filterValue.getDogAge());
                    humanAgeNum.setText(filterValue.getHumanAge());
                    if(filterValue.getDogGender().size() == 2)
                    {
                        dogMale.setChecked(true);
                        dogFemale.setChecked(true);
                    }
                    if(filterValue.getDogGender().size() == 1)
                    {
                        String dogGender = filterValue.getDogGender().get(0);
                        if(dogGender.equals("m"))
                        {
                            dogMale.setChecked(true);
                        }
                        if(dogGender.equals("f"))
                        {
                            dogFemale.setChecked(true);
                        }
                    }
                    if(filterValue.getHumanGender().size() == 2)
                    {
                        humanMale.setChecked(true);
                        humanFemale.setChecked(true);
                    }
                    if(filterValue.getHumanGender().size() == 1)
                    {
                        String humanGender = filterValue.getHumanGender().get(0);
                        if(humanGender.equals("m"))
                        {
                            humanMale.setChecked(true);
                        }
                        if(humanGender.equals("f"))
                        {
                            humanFemale.setChecked(true);
                        }
                    }
                    if(filterValue.getSpayedNeutered() == true)
                    {
                        spayedNeutered.setChecked(true);
                    }
                    if(filterValue.getShotsUpToDate() == true)
                    {
                        shotsUpToDate.setChecked(true);
                    }
                    int indexBreed = 0;
                    for(int i = 0; i < breeds.size(); i++)
                    {
                        if(filterValue.getBreed().equals(breeds.get(i)))
                        {
                            indexBreed = i;
                        }
                    }
                    breed.setSelection(indexBreed);
                    int indexDogAgeDesc = 0;
                    for(int i = 0; i < dogAgeDescriptionValues.size(); i++)
                    {
                        if(filterValue.getDogAgeDesc().equals(dogAgeDescriptionValues.get(i)))
                        {
                            indexDogAgeDesc = i;
                        }
                    }
                    dogAgeDescription.setSelection(indexDogAgeDesc);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("EmailPassword", error.getMessage());
            }
        };
        filterRef.addListenerForSingleValueEvent(listener);
    }
        @Override
    public void onClick(View v) {
        //save filters to the database to auto fill for later
        if(saveFilters.isChecked())
        {
            String dogAge = dogAgeNum.getText().toString();
            String humanAge = humanAgeNum.getText().toString();
            String dogAgeDesc = dogAgeDescription.getSelectedItem().toString();
            String breedVal = breed.getSelectedItem().toString();
            boolean spayNeutered = false;
            boolean shotsUpToDateVal = false;
            if (spayedNeutered.isChecked() == true)
            {
                spayNeutered = true;
            }
            if (shotsUpToDate.isChecked() == true)
            {
                shotsUpToDateVal = true;
            }
            ArrayList<String> dogGenderVal = new ArrayList<String>();
            ArrayList<String> humanGenderVal = new ArrayList<String>();
            if(humanMale.isChecked() == true)
            {
                humanGenderVal.add("m");
            }
            if(humanFemale.isChecked() == true)
            {
                humanGenderVal.add("f");
            }
            if(dogMale.isChecked() == true)
            {
                dogGenderVal.add("m");
            }
            if(dogFemale.isChecked() == true)
            {
                dogGenderVal.add("f");
            }
            Filter f = new Filter(dogAge, dogAgeDesc, dogGenderVal, spayNeutered, shotsUpToDateVal, breedVal, humanAge, humanGenderVal);
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference filterRef = rootRef.child("users").child(user.getUid()).child("filters");
            filterRef.setValue(f);
        }
        Intent intent = new Intent(getBaseContext(), SearchResultsActivity.class);
        intent.putExtra("SORTED_RESULTS", SearchThroughDatabase());
        startActivity(intent);
    }

    public ArrayList<User> SearchThroughDatabase()
    {
        ArrayList<User> searchReturn = new ArrayList<User>();

        return Sort(searchReturn);
    }

    public ArrayList<User> Sort(ArrayList<User> givenArray)
    {
        ArrayList<User> sortReturn = new ArrayList<User>();

        return sortReturn;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        //an item is selected. Retrieve it by using parent.getItemPosition(pos)
        //if(view == citySpinner) {
            //citySpinner.setSelection(pos);
            //city = parent.getItemAtPosition(pos);
        //}
        //if(view == stateSpinner) {
            //stateSpinner.setSelection(pos);
        //}
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}