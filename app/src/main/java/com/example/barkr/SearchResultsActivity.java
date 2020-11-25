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
        User u = new User("livelikedragons");
        u.setDogProfiles();
        HumanProfile hp = new HumanProfile("Human", "f", "Texas", "", "kdperez", "hello", 12);
        u.setHumanProfile(hp);
        userList.add(u);


        resultsRecycler = (RecyclerView) findViewById(R.id.RecyclerViewSearchResults);
        adapter = new SearchResultsAdapter(SearchResultsActivity.this, userList);
        resultsRecycler.setAdapter(adapter);
        resultsRecycler.setLayoutManager(new LinearLayoutManager(this));



    }

    @Override
    public void onClick(View v)
    {

    }
}
