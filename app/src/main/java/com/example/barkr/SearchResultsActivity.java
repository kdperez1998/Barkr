package com.example.barkr;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView resultsRecycler;
    ArrayList<User> userList;
    static SearchResultsAdapter adapter;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        userList = new ArrayList<User>();
        /*HumanProfile hp = new HumanProfile("name", "f", "Tyler, Texas", "903372", "hello", "12/12/1222");
        DogProfile dogProfile = new DogProfile("pet", "corgi", "f", false, false, "hello", "12/12/1212");
        ArrayList<DogProfile> dp = new ArrayList<DogProfile>();
        dp.add(dogProfile);
        userList.add(new User("email", hp, dp, "userId"));
         */

        //TODO get extra value from the previous activity/fragment
        userList = (ArrayList<User>) getIntent().getSerializableExtra("SORTED_RESULTS");

        //System.out.println(userList.size());
        resultsRecycler = (RecyclerView) findViewById(R.id.RecyclerViewSearchResults);
        adapter = new SearchResultsAdapter(SearchResultsActivity.this, userList);
        resultsRecycler.setAdapter(adapter);
        resultsRecycler.setLayoutManager(new LinearLayoutManager(this));



    }

    //on click for buttons outside of recycler view
    @Override
    public void onClick(View v)
    {

    }
}
