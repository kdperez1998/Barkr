package com.example.barkr;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView resultsRecyclerView;
    ArrayList<User> userList;
    SearchResultsAdapter adapter;
    ImageButton backButton;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        userList = new ArrayList<User>();
        /*HumanProfile hp = new HumanProfile("name", "f", "Tyler, Texas", "903372", "hello", "12/12/1222");
        DogProfile dogProfile = new DogProfile("pet", "corgi", "f", false, false, "hello", "12/12/1212");
        ArrayList<DogProfile> dp = new ArrayList<DogProfile>();
        dp.add(dogProfile);
        userList.add(new User("email", hp, dp, "userId"));
         */

        //get extra value from previous activity
        userList = (ArrayList<User>) getIntent().getSerializableExtra("SORTED_RESULTS");


        //set extra value to be shown in the adapter
        resultsRecyclerView = (RecyclerView) findViewById(R.id.RecyclerViewSearchResults);
        adapter = new SearchResultsAdapter(SearchResultsActivity.this, userList);
        resultsRecyclerView.setAdapter(adapter);
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    //on click for buttons outside of recycler view
    @Override
    public void onClick(View v)
    {

    }
}
