package com.example.barkr;

import android.R.layout;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener
{
    EditText editHumanName, editHumanDateOfBirth, editEmail, editPhoneNumber, editHumanBio;
    ImageView profileImage, profilePicture;
    Button finish;
    Spinner stateSpinner, citySpinner;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editHumanName = findViewById(R.id.EditOwnerName);
        editHumanDateOfBirth = findViewById(R.id.EditDateofBirth);
        editEmail = findViewById(R.id.EditEmail);
        editPhoneNumber = findViewById(R.id.editTextPhone);
        editHumanBio = findViewById(R.id.editBio);

        finish = findViewById(R.id.buttonFinishProfile);
        finish.setOnClickListener((View.OnClickListener) this);

        //initialize and set values for the state spinner and its adapter
        stateSpinner = findViewById(R.id.Spinner_State);
        stateSpinner.setOnItemSelectedListener(this);
        List<String> states = new ArrayList<String>();
        states.add("Texas");
        states.add("Oklahoma");
        ArrayAdapter adapterStates = new ArrayAdapter(this, layout.simple_spinner_item, states);
        adapterStates.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapterStates);

        //initialize and set values for the city spinner and its adapter
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

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        //an item is selected. Retrieve it by using parent.getItemPosition(pos)
        if(view == citySpinner) {
            //citySpinner.setSelection(pos);
        }
        if(view == stateSpinner) {
            //stateSpinner.setSelection(pos);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}