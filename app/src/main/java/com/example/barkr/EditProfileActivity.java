package com.example.barkr;

import android.R.layout;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener
{
    EditText editHumanName, editHumanDateOfBirth, editPhoneNumber, editHumanBio, editDogDateOfBirth, editDogName, editDogBio;
    ImageView humanImage;
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
    Toolbar toolbar;
    int RESULT_LOAD_IMAGE = 1;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        humanImage = findViewById(R.id.ImageofHuman);
        humanImage.setImageResource(R.drawable.ic_image_white_36pt_3x);
        humanImage.setOnClickListener(this);

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
        states = new ArrayList<String>();

        //add states
        states.add("Select State");
        states.add("Alabama");
        states.add("Alaska");
        states.add("Arizona");
        states.add("Arkansas");
        states.add("California");
        states.add("Colorado");
        states.add("Connecticut");
        states.add("Delaware");
        states.add("Florida");
        states.add("Georgia");
        states.add("Hawaii");
        states.add("Idaho");
        states.add("Illinois");
        states.add("Indiana");
        states.add("Iowa");
        states.add("Kansas");
        states.add("Kentucky");
        states.add("Louisiana");
        states.add("Maine");
        states.add("Maryland");
        states.add("Massachusetts");
        states.add("Michigan");
        states.add("Minnesota");
        states.add("Mississippi");
        states.add("Missouri");
        states.add("Montana");
        states.add("Nebraska");
        states.add("Nevada");
        states.add("New Hampshire");
        states.add("New Jersey");
        states.add("New Mexico");
        states.add("New York");
        states.add("North Carolina");
        states.add("North Dakota");
        states.add("Ohio");
        states.add("Oklahoma");
        states.add("Oregon");
        states.add("Pennsylvania");
        states.add("Rhode Island");
        states.add("South Carolina");
        states.add("South Dakota");
        states.add("Tennessee");
        states.add("Texas");
        states.add("Utah");
        states.add("Vermont");
        states.add("Virginia");
        states.add("Washington");
        states.add("West Virginia");
        states.add("Wisconsin");
        states.add("Wyoming");
/*
        try
        {
            //parsing a CSV file into BufferedReader class constructor
            InputStream is = this.getResources().openRawResource(this.getResources().getIdentifier("us_cities_states_countries", "raw", getPackageName()));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((br.readLine()) != null)   //returns a Boolean value
            {      // use comma as separator
                String line = br.readLine();
                String[] values = line.split("\\|");
                for(String value : values)
                {
                    System.out.println(value);
                }
                System.out.println();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


 */
        ArrayAdapter adapterStates = new ArrayAdapter(this, layout.simple_spinner_item, states);
        adapterStates.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapterStates);

        //initialize and set values for the city spinner and its adapter
        //this is currently using sample values, will come back and put api values later
        citySpinner = findViewById(R.id.spinner_City);
        citySpinner.setOnItemSelectedListener(this);

        //add cities
        cities.add("Select City");
        cities.add("Tyler");
        cities.add("Lindale");


        ArrayAdapter adapterCities = new ArrayAdapter(this, layout.simple_spinner_item, cities);
        adapterCities.setDropDownViewResource(layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapterCities);

        humanGenderSpinner = findViewById(R.id.spinnerHumanGender);
        humanGenderSpinner.setOnItemSelectedListener(this);
        genders = new ArrayList<String>();
        genders.add("Select Gender");
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

        //add all breeds from AKC website, manually added since an API was not found in time
        breeds.add("Select breed");
        breeds.add("Affenpinscher");
        breeds.add("Afghan Hound");
        breeds.add("Airedale Terrier");
        breeds.add("Akita");
        breeds.add("Alaskan Malamute");
        breeds.add("American Bulldog");
        breeds.add("American English Coonhound");
        breeds.add("American Eskimo Dog");
        breeds.add("American Foxhound");
        breeds.add("American Hairless Terrier");
        breeds.add("American Leopard Hound");
        breeds.add("American Staffordshire Terrier");
        breeds.add("American Water Spaniel");
        breeds.add("Anatolian Shepherd Dog");
        breeds.add("Appenzeller Sennenhund");
        breeds.add("Australian Cattle Dog");
        breeds.add("Australian Kelpie");
        breeds.add("Australian Shepherd");
        breeds.add("Australian Stumpy Tail Cattle Dog");
        breeds.add("Australian Terrier");
        breeds.add("Azawakh");
        breeds.add("Barbet");
        breeds.add("Basenji");
        breeds.add("Basset Fauve de Bretagne");
        breeds.add("Basset Hound");
        breeds.add("Bavarian Mountain Scent Hound");
        breeds.add("Beagle");
        breeds.add("Bearded Collie");
        breeds.add("Beauceron");
        breeds.add("Bedlington Terrier");
        breeds.add("Belgian Laekenois");
        breeds.add("Belgian Malinois");
        breeds.add("Belgian Sheepdog");
        breeds.add("Belgian Tervuren");
        breeds.add("Bergamasco Sheepdog");
        breeds.add("Berger Picard");
        breeds.add("Bernese Mountain Dog");
        breeds.add("Bichon Frise");
        breeds.add("Biewer Terrier");
        breeds.add("Black and Tan Coonhound");
        breeds.add("Black Russian Terrier");
        breeds.add("Bloodhound");
        breeds.add("Bluetick Coonhound");
        breeds.add("Boerboel");
        breeds.add("Bohemian Shepherd");
        breeds.add("Bolognese");
        breeds.add("Border Collie");
        breeds.add("Border Terrier");
        breeds.add("Borzoi");
        breeds.add("Boston Terrier");
        breeds.add("Bouvier des Flandres");
        breeds.add("Boxer");
        breeds.add("Boykin Spaniel");
        breeds.add("Bracco Italiano");
        breeds.add("Braque du Bourbonnais");
        breeds.add("Braque Francais Pyrenean");
        breeds.add("Briard");
        breeds.add("Brittany");
        breeds.add("Broholmer");
        breeds.add("Brussels Griffon");
        breeds.add("Bull Terrier");
        breeds.add("Bulldog");
        breeds.add("Bullmastiff");
        breeds.add("Cairn Terrier");
        breeds.add("Canaan Dog");
        breeds.add("Cane Corso");
        breeds.add("Cardigan Welsh Corgi");
        breeds.add("Carolina Dog");
        breeds.add("Catahoula Leopard Dog");
        breeds.add("Caucasian Shepherd Dog");
        breeds.add("Cavalier King Charles Spaniel");
        breeds.add("Central Asian Shepherd Dog");
        breeds.add("Cesky Terrier");
        breeds.add("Chesapeake Bay Retriever");
        breeds.add("Chihuahua");
        breeds.add("Chinese Crested");
        breeds.add("Chinese Shar-Pei");
        breeds.add("Chinook");
        breeds.add("Chow Chow");
        breeds.add("Cirneco dell’Etna");
        breeds.add("Clumber Spaniel");
        breeds.add("Cocker Spaniel");
        breeds.add("Collie");
        breeds.add("Coton de Tulear");
        breeds.add("Croatian Sheepdog");
        breeds.add("Curly-Coated Retriever");
        breeds.add("Czechoslovakian Vlcak");
        breeds.add("Dachshund");
        breeds.add("Dalmatian");
        breeds.add("Dandie Dinmont Terrier");
        breeds.add("Danish-Swedish Farmdog");
        breeds.add("Deutscher Wachtelhund");
        breeds.add("Doberman Pinscher");
        breeds.add("Dogo Argentino");
        breeds.add("Dogue de Bordeaux");
        breeds.add("Drentsche Patrijshond");
        breeds.add("Drever");
        breeds.add("Dutch Shepherd");
        breeds.add("English Cocker Spaniel");
        breeds.add("English Foxhound");
        breeds.add("English Setter");
        breeds.add("English Springer Spaniel");
        breeds.add("English Toy Spaniel");
        breeds.add("Entlebucher Mountain Dog");
        breeds.add("Estrela Mountain Dog");
        breeds.add("Eurasier");
        breeds.add("Field Spaniel");
        breeds.add("Finnish Lapphund");
        breeds.add("Finnish Spitz");
        breeds.add("Flat-Coated Retriever");
        breeds.add("French Bulldog");
        breeds.add("French Spaniel");
        breeds.add("German Longhaired Pointer");
        breeds.add("German Pinscher");
        breeds.add("German Shepherd");
        breeds.add("German Shorthaired Pointer");
        breeds.add("German Spitz");
        breeds.add("German Wirehaired Pointer");
        breeds.add("Giant Schnauzer");
        breeds.add("Glen of Imaal Terrier");
        breeds.add("Golden Retriever");
        breeds.add("Gordon Setter");
        breeds.add("Grand Basset Griffon Vendéen");
        breeds.add("Great Dane");
        breeds.add("Great Pyrenees");
        breeds.add("Greater Swiss Mountain Dog");
        breeds.add("Greyhound");
        breeds.add("Hamiltonstovare");
        breeds.add("Hanoverian Scenthound");
        breeds.add("Harrier");
        breeds.add("Havanese");
        breeds.add("Hokkaido");
        breeds.add("Hovawart");
        breeds.add("Ibizan Hound");
        breeds.add("Icelandic Sheepdog");
        breeds.add("Irish Red and White Setter");
        breeds.add("Irish Setter");
        breeds.add("Irish Terrier");
        breeds.add("Irish Water Spaniel");
        breeds.add("Irish Wolfhound");
        breeds.add("Italian Greyhound");
        breeds.add("Jagdterrier");
        breeds.add("Japanese Akitainu");
        breeds.add("Japanese Chin");
        breeds.add("Japanese Spitz");
        breeds.add("Jindo");
        breeds.add("Kai Ken");
        breeds.add("Karelian Bear Dog");
        breeds.add("Keeshond");
        breeds.add("Kerry Blue Terrier");
        breeds.add("Kishu Ken");
        breeds.add("Komondor");
        breeds.add("Kromfohrlander");
        breeds.add("Kuvasz");
        breeds.add("Labrador Retriever");
        breeds.add("Lagotto Romagnolo");
        breeds.add("Lakeland Terrier");
        breeds.add("Lancashire Heeler");
        breeds.add("Lapponian Herder");
        breeds.add("Leonberger");
        breeds.add("Lhasa Apso");
        breeds.add("Löwchen");
        breeds.add("Maltese");
        breeds.add("Manchester Terrier (Standard)");
        breeds.add("Manchester Terrier (Toy)");
        breeds.add("Mastiff");
        breeds.add("Miniature American Shepherd");
        breeds.add("Miniature Bull Terrier");
        breeds.add("Miniature Pinscher");
        breeds.add("Miniature Schnauzer");
        breeds.add("Mixed");
        breeds.add("Mountain Cur");
        breeds.add("Mudi");
        breeds.add("Neapolitan Mastiff");
        breeds.add("Nederlandse Kooikerhondje");
        breeds.add("Newfoundland");
        breeds.add("Norfolk Terrier");
        breeds.add("Norrbottenspets");
        breeds.add("Norwegian Buhund");
        breeds.add("Norwegian Elkhound");
        breeds.add("Norwegian Lundehund");
        breeds.add("Norwich Terrier");
        breeds.add("Nova Scotia Duck Tolling Retriever");
        breeds.add("Old English Sheepdog");
        breeds.add("Otterhound");
        breeds.add("Papillon");
        breeds.add("Parson Russell Terrier");
        breeds.add("Pekingese");
        breeds.add("Pembroke Welsh Corgi");
        breeds.add("Perro de Presa Canario");
        breeds.add("Peruvian Inca Orchid");
        breeds.add("Petit Basset Griffon Vendéen");
        breeds.add("Pharaoh Hound");
        breeds.add("Plott Hound");
        breeds.add("Pointer");
        breeds.add("Polish Lowland Sheepdog");
        breeds.add("Pomeranian");
        breeds.add("Poodle (Miniature)");
        breeds.add("Poodle (Standard)");
        breeds.add("Poodle (Toy)");
        breeds.add("Porcelaine");
        breeds.add("Portuguese Podengo");
        breeds.add("Portuguese Podengo Pequeno");
        breeds.add("Portuguese Pointer");
        breeds.add("Portuguese Sheepdog");
        breeds.add("Portuguese Water Dog");
        breeds.add("Pudelpointer");
        breeds.add("Pug");
        breeds.add("Puli");
        breeds.add("Pumi");
        breeds.add("Pyrenean Mastiff");
        breeds.add("Pyrenean Shepherd");
        breeds.add("Rafeiro do Alentejo");
        breeds.add("Rat Terrier");
        breeds.add("Redbone Coonhound");
        breeds.add("Rhodesian Ridgeback");
        breeds.add("Romanian Mioritic Shepherd Dog");
        breeds.add("Rottweiler");
        breeds.add("Russell Terrier");
        breeds.add("Russian Toy");
        breeds.add("Russian Tsvetnaya Bolonka");
        breeds.add("Saint Bernard");
        breeds.add("Saluki");
        breeds.add("Samoyed");
        breeds.add("Schapendoes");
        breeds.add("Schipperke");
        breeds.add("Scottish Deerhound");
        breeds.add("Scottish Terrier");
        breeds.add("Sealyham Terrier");
        breeds.add("Segugio Italiano");
        breeds.add("Shetland Sheepdog");
        breeds.add("Shiba Inu");
        breeds.add("Shih Tzu");
        breeds.add("Shikoku");
        breeds.add("Siberian Husky");
        breeds.add("Silky Terrier");
        breeds.add("Skye Terrier");
        breeds.add("Sloughi");
        breeds.add("Slovakian Wirehaired Pointer");
        breeds.add("Slovensky Cuvac");
        breeds.add("Slovensky Kopov");
        breeds.add("Small Munsterlander");
        breeds.add("Smooth Fox Terrier");
        breeds.add("Soft Coated Wheaten Terrier");
        breeds.add("Spanish Mastiff");
        breeds.add("Spanish Water Dog");
        breeds.add("Spinone Italiano");
        breeds.add("Stabyhoun");
        breeds.add("Staffordshire Bull Terrier");
        breeds.add("Standard Schnauzer");
        breeds.add("Sussex Spaniel");
        breeds.add("Swedish Lapphund");
        breeds.add("Swedish Vallhund");
        breeds.add("Taiwan Dog");
        breeds.add("Teddy Roosevelt Terrier");
        breeds.add("Thai Ridgeback");
        breeds.add("Tibetan Mastiff");
        breeds.add("Tibetan Spaniel");
        breeds.add("Tibetan Terrier");
        breeds.add("Tornjak");
        breeds.add("Tosa");
        breeds.add("Toy Fox Terrier");
        breeds.add("Transylvanian Hound");
        breeds.add("Treeing Tennessee Brindle");
        breeds.add("Treeing Walker Coonhound");
        breeds.add("Vizsla");
        breeds.add("Weimaraner");
        breeds.add("Welsh Springer Spaniel");
        breeds.add("Welsh Terrier");
        breeds.add("West Highland White Terrier");
        breeds.add("Wetterhoun");
        breeds.add("Whippet");
        breeds.add("Wire Fox Terrier");
        breeds.add("Wirehaired Pointing Griffon");
        breeds.add("Wirehaired Vizsla");
        breeds.add("Working Kelpie");
        breeds.add("Xoloitzcuintli");
        breeds.add("Yakutian Laika");
        breeds.add("Yorkshire Terrier");


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
                    System.out.println(state);
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
                    //states
                    int indexStates = 0;
                    for(int i = 0; i < states.size(); i++)
                    {
                        if(states.get(i).equals(state))
                        {
                            indexStates = i;
                        }
                    }
                    stateSpinner.setSelection(indexStates);
                    //gender for human
                    int indexHumanGender = 0;
                    for(int i = 0; i < genders.size(); i++)
                    {
                        if((genders.get(i).equals("Female") && humanGender.equals("f") || (genders.get(i).equals("Male") && humanGender.equals("m"))))
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
                        if((genders.get(i).equals("Female") && dogGender.equals("f") || (genders.get(i).equals("Male") && dogGender.equals("m"))))
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
            if(checkValues()) {
                //rewrite value to the database
                //this is not checking if any values are empty or correct values! Will be changing soon//TODO
                String name = editHumanName.getText().toString();
                String gender = "";
                if (humanGenderSpinner.getSelectedItem().toString().equals("Female")) {
                    gender = "f";
                } else if (humanGenderSpinner.getSelectedItem().toString().equals("Male")) {
                    gender = "m";
                }
                String location = citySpinner.getSelectedItem().toString() + ", " + stateSpinner.getSelectedItem().toString();
                String phone = PhoneNumberUtils.formatNumber(editPhoneNumber.getText().toString());
                String bio = editHumanBio.getText().toString();
                String birthdate = editHumanDateOfBirth.getText().toString();
                String email = user.getEmail();
                HumanProfile hp = new HumanProfile(name, gender, location, phone, bio, birthdate);
                ArrayList<DogProfile> dogProfiles = new ArrayList<DogProfile>();
                String dogName = editDogName.getText().toString();
                String dogBreed = dogBreedSpinner.getSelectedItem().toString();
                String dogGender = "";
                if (dogGenderSpinner.getSelectedItem().toString().equals("Female")) {
                    dogGender = "f";
                } else if (dogGenderSpinner.getSelectedItem().toString().equals("Male")) {
                    dogGender = "m";
                }
                boolean spayNeuter = false;
                boolean shotsUpDate = false;
                if (spayedNeutered.isChecked() == true) {
                    spayNeuter = true;
                }
                if (shotsUpToDate.isChecked() == true) {
                    shotsUpDate = true;
                }
                String dogBio = editDogBio.getText().toString();
                String dogBirthdate = editDogDateOfBirth.getText().toString();
                DogProfile dp = new DogProfile(dogName, dogBreed, dogGender, spayNeuter, shotsUpDate, dogBio, dogBirthdate);
                dogProfiles.add(dp);
                User newUser = new User(email, hp, dogProfiles, user.getUid());
                myRef.child("users").child(user.getUid()).setValue(newUser);

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("FRAGMENT_TO_LOAD", 3);
                startActivity(intent);
            }
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

        if(v == humanImage)
        {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.ImageofHuman);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
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

    public boolean checkValues()
    {
        boolean returnValue = true;

        if(editHumanName.getText().toString().equals(""))
        {
            editHumanName.setError("Cannot be left blank");
            returnValue = false;
        }
        if(editHumanDateOfBirth.getText().toString().equals(""))
        {
            editHumanDateOfBirth.setError("Cannot be left blank");
            returnValue = false;
        }
        if(humanGenderSpinner.getSelectedItem().equals("Select Gender"))
        {
            ((TextView) humanGenderSpinner.getSelectedView()).setError("Must select a gender");
            returnValue = false;
        }
        if(stateSpinner.getSelectedItem().equals("Select State"))
        {
            ((TextView) stateSpinner.getSelectedView()).setError("Must select a state");
            returnValue = false;
        }
        if(citySpinner.getSelectedItem().equals("Select City"))
        {
            ((TextView) citySpinner.getSelectedView()).setError("Must select a city");
            returnValue = false;
        }
        if(editPhoneNumber.getText().toString().equals(""))
        {
            editPhoneNumber.setError("Cannot be left blank");
            returnValue = false;
        }
        if(editDogName.getText().toString().equals(""))
        {
            editDogName.setError("Cannot be left blank");
            returnValue = false;
        }
        if(editDogDateOfBirth.getText().toString().equals(""))
        {
            editDogDateOfBirth.setError("Cannot be left blank");
            returnValue = false;
        }
        if(dogGenderSpinner.getSelectedItem().equals("Select Gender"))
        {
            ((TextView) dogGenderSpinner.getSelectedView()).setError("Must select a gender");
            returnValue = false;
        }
        if(dogBreedSpinner.getSelectedItem().equals("Select Breed"))
        {
            ((TextView) dogBreedSpinner.getSelectedView()).setError("Must select a breed");
            returnValue = false;
        }
        return returnValue;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}