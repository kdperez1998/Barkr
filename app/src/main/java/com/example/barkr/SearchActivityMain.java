package com.example.barkr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchActivityMain extends Fragment implements View.OnClickListener{

    RecyclerView resultsRecycler;
    ArrayList<User> userList;
    static SearchResultsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userList = new ArrayList<User>();
        //User u = new User("livelikedragons");
        //u.setDogProfiles();
        HumanProfile hp = new HumanProfile("Human", "f", "Texas", "", "kdperez", "hello", 12);
        //u.setHumanProfile(hp);
        //userList.add(u);
        //userList.add(u);
        //userList.add(u);

        resultsRecycler = (RecyclerView) getView().findViewById(R.id.RecyclerViewSearchMain);
        adapter = new SearchResultsAdapter(getActivity(), userList);
        resultsRecycler.setAdapter(adapter);
        //here the second attribute for GridLayoutManager is 2 because this is the amount of columns we will have in the recycler view
        resultsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));

    }


    //this was code from when this was not a fragment
   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);

        navView = findViewById(R.id.navigation);

        userList = new ArrayList<User>();
        //User u = new User("livelikedragons");
        //u.setDogProfiles();
        HumanProfile hp = new HumanProfile("Human", "f", "Texas", "", "kdperez", "hello", 12);
        //u.setHumanProfile(hp);
        //userList.add(u);
        //userList.add(u);
        //userList.add(u);

        resultsRecycler = (RecyclerView) findViewById(R.id.RecyclerViewSearchMain);
        adapter = new SearchResultsAdapter(SearchActivityMain.this, userList);
        resultsRecycler.setAdapter(adapter);
        //here the second attribute for GridLayoutManager is 2 because this is the amount of columns we will have in the recycler view
        resultsRecycler.setLayoutManager(new GridLayoutManager(this, 2));



    }
*/
    @Override
    public void onClick(View v)
    {

    }

}