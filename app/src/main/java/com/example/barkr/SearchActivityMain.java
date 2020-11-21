package com.example.barkr;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchActivityMain extends AppCompatActivity implements View.OnClickListener{

    RecyclerView resultsRecycler;
    ArrayList<User> userList;
    static SearchResultsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);

        userList = new ArrayList<User>();
        User u = new User("livelikedragons");
        u.setDogProfiles();
        HumanProfile hp = new HumanProfile("Human", "f", "Texas", "", "kdperez", "hello", 12);
        u.setHumanProfile(hp);
        userList.add(u);
        userList.add(u);
        userList.add(u);

        resultsRecycler = (RecyclerView) findViewById(R.id.RecyclerViewSearchMain);
        adapter = new SearchResultsAdapter(SearchActivityMain.this, userList);
        resultsRecycler.setAdapter(adapter);
        //here the second attribute for GridLayoutManager is 2 because this is the amount of columns we will have in the recycler view
        resultsRecycler.setLayoutManager(new GridLayoutManager(this, 2));



    }

    @Override
    public void onClick(View v)
    {

    }

}