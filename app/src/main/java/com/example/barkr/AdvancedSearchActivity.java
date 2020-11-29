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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvancedSearchActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener
{

    EditText dogAgeNum, humanAgeNum, milesNum;
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
        milesNum = findViewById(R.id.editTextMiles);

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
                    milesNum.setText(Integer.toString(filterValue.getMiles()));
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
            int miles = Integer.parseInt(milesNum.getText().toString());
            f = new Filter(dogAge, dogAgeDesc, dogGenderVal, spayNeutered, shotsUpToDateVal, breedVal, humanAge, humanGenderVal, miles);
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference filterRef = rootRef.child("users").child(user.getUid()).child("filters");
            filterRef.setValue(f);
        }
        //send values to another activity or screen08/26/1998
        Intent intent = new Intent(getBaseContext(), SearchResultsActivity.class);
        intent.putExtra("SORTED_RESULTS", SearchThroughDatabase());
        startActivity(intent);
    }

    public ArrayList<User> SearchThroughDatabase()
    {
        ArrayList<User> searchReturn = new ArrayList<User>();

        //receive values from the database other than the current user
        final String currUserId = user.getUid();
        final ArrayList<User> databaseValues = new ArrayList<User>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot userSnapshot: snapshot.getChildren())
                {
                    //checks if the current user it is looking at has the same userid as the person signed in
                    //if it doesnt, add it to the array list to search through
                    if(!(userSnapshot.getValue(User.class).getUserId().equals(currUserId)))
                    {
                        databaseValues.add(userSnapshot.getValue(User.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("AdvancedSearch", "loadPost:onCancelled", error.toException());
            }
        });
        //now we begin the run through the values to see if it matches the filters
        //this list is going to split the values by the number of values that matched the filter
        //this is so we can sort by most relevant and then by location
        HashMap<Integer, ArrayList<User>> splitValues = new HashMap<Integer, ArrayList<User>>();
        for(User u: databaseValues)
        {
            //First check if the value is within the given miles TODO

            //if it is, compare it to the current filter data
            int numMatching = u.numValuesMatch(f);
            //Add the value to the array list at the location of how many values it matches
            //checks if list already exists there. if not, create one, then add the user value at position numMatching
            splitValues.putIfAbsent(numMatching, new ArrayList<User>());
            splitValues.get(numMatching).add(u);
        }
        //iterate through the lists in the hashmap
        for(Map.Entry<Integer, ArrayList<User>> ee : splitValues.entrySet())
        {
            int key = ee.getKey();
            ArrayList<User> values = ee.getValue();

            //sort the values in the current list by location TODO

        }

        //add the values to the search return in the order of most relevant to least relevant in the hashmap of lists
        //here the value is 7 because there can be up to 7 values that match the filter given
        for(int i = 7; i >= 0; i--)
        {
            if(splitValues.containsKey(i))
            {
                ArrayList<User> currUserList = splitValues.get(i);
                for(int j = 0; j < currUserList.size(); j++)
                {
                    searchReturn.add(currUserList.get(j));
                }
            }
        }
        return searchReturn;
    }

    public ArrayList<User> Sort(ArrayList<User> givenArray)
    {
        //sort by location distance TODO
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