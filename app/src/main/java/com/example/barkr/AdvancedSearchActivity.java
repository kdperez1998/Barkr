package com.example.barkr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import androidx.appcompat.widget.Toolbar;

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
    Toolbar toolbar;
    ArrayList<User> databaseValues;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        databaseValues = new ArrayList<User>();

        breeds = new ArrayList<String>();
        //add all breeds from the AKC website
        breeds.add("");
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


        dogAgeDescriptionValues = new ArrayList<String>();
        dogAgeDescriptionValues.add("");
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
                Log.d("AdvancedSearch", error.getMessage());
            }
        };
        filterRef.addListenerForSingleValueEvent(listener);
    }
        @Override
    public void onClick(View v) {
        if(v == search) {
            if((!dogAgeNum.getText().toString().equals("")))
            {
                if((dogAgeDescription.getSelectedItem().equals("")))
                {
                    dogAgeNum.setError("Please specify days/months/years on the right when selecting an age");
                }
            }
            if(!dogAgeDescription.getSelectedItem().equals(""))
            {
                if(dogAgeNum.getText().toString().equals(""))
                {
                    dogAgeNum.setError("Must not be left blank when given months/days/years");
                }
            }
            else
            {
                //save filters to the database to auto fill for later
                String dogAge = dogAgeNum.getText().toString();
                String humanAge = humanAgeNum.getText().toString();
                String dogAgeDesc = dogAgeDescription.getSelectedItem().toString();
                String breedVal = breed.getSelectedItem().toString();
                boolean spayNeutered = false;
                boolean shotsUpToDateVal = false;
                if (spayedNeutered.isChecked() == true) {
                    spayNeutered = true;
                }
                if (shotsUpToDate.isChecked() == true) {
                    shotsUpToDateVal = true;
                }
                ArrayList<String> dogGenderVal = new ArrayList<String>();
                ArrayList<String> humanGenderVal = new ArrayList<String>();
                if (humanMale.isChecked() == true) {
                    humanGenderVal.add("m");
                }
                if (humanFemale.isChecked() == true) {
                    humanGenderVal.add("f");
                }
                if (dogMale.isChecked() == true) {
                    dogGenderVal.add("m");
                }
                if (dogFemale.isChecked() == true) {
                    dogGenderVal.add("f");
                }
                int miles = Integer.parseInt(milesNum.getText().toString());
                f = new Filter(dogAge, dogAgeDesc, dogGenderVal, spayNeutered, shotsUpToDateVal, breedVal, humanAge, humanGenderVal, miles);
                if (saveFilters.isChecked()) {
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference filterRef = rootRef.child("users").child(user.getUid()).child("filters");
                    filterRef.setValue(f);
                }

                //receive values from the database other than the current user
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference().child("users");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseValues = new ArrayList<User>();
                        String currUserId = user.getUid();
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            //checks if the current user it is looking at has the same userid as the person signed in
                            //if it doesnt, add it to the array list to search through
                            if (!(userSnapshot.getValue(User.class).getUserId().equals(currUserId))) {
                                databaseValues.add(userSnapshot.getValue(User.class));
                            }

                        }
                        //send values to another activity or screen
                        ArrayList<User> results = SearchThroughDatabase(databaseValues);
                        Intent intent = new Intent(AdvancedSearchActivity.this, SearchResultsActivity.class);
                        intent.putExtra("SORTED_RESULTS", results);
                        startActivity(intent);

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("AdvancedSearch", "loadPost:onCancelled", error.toException());
                    }
                });


            }
        }
    }

    public ArrayList<User> SearchThroughDatabase(ArrayList<User> searchList)
    {
        ArrayList<User> searchReturn = new ArrayList<User>();

        //now we begin the run through the values to see if it matches the filters
        //this list is going to split the values by the number of values that matched the filter
        //this is so we can sort by most relevant and then by location
        HashMap<Integer, ArrayList<User>> splitValues = new HashMap<Integer, ArrayList<User>>();
        for(User u: searchList)
        {
            //First check if the value is within the given miles TODO

            //if it is, compare it to the current filter data
            int numMatching = u.numValuesMatch(f);
            //Add the value to the array list at the location of how many values it matches
            //checks if list already exists there. if not, create one, then add the user value at position numMatching
            splitValues.putIfAbsent(numMatching, new ArrayList<User>());
            splitValues.get(numMatching).add(u);
            System.out.println(numMatching);
            System.out.println(u.getHumanProfile().getname());
            System.out.println(u.getDogProfiles().get(0).getbreed());
            System.out.println("~~~~~~~~~~~~~~~~");
        }
        //iterate through the lists in the hashmap
        for(Map.Entry<Integer, ArrayList<User>> ee : splitValues.entrySet())
        {
            int key = ee.getKey();
            ArrayList<User> values = ee.getValue();
            for(User value : values)
            {
                System.out.println(value.getHumanProfile().getname());
            }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}