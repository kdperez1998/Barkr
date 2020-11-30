package com.example.barkr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchActivityMain extends Fragment implements View.OnClickListener{

    RecyclerView resultsRecycler;
    ArrayList<User> userList;
    static SearchResultsAdapter adapter;
    Button advancedSearch;
    FirebaseUser user;
    EditText searchBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userList = new ArrayList<User>();

        user = FirebaseAuth.getInstance().getCurrentUser();

        //System.out.println(user.getUid());
        //HumanProfile hp = new HumanProfile("name", "f", "Tyler, Texas", "903372", "hello", "12/12/1222");
        //DogProfile dogProfile = new DogProfile("pet", "corgi", "f", false, false, "hello", "12/12/1212");
        //ArrayList<DogProfile> dp = new ArrayList<DogProfile>();
        //dp.add(dogProfile);
        //userList.add(new User(user.getEmail(), hp, dp, user.getUid()));
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child("users").child(user.getUid());
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User u = snapshot.getValue(User.class);
                    userList.add(u);
                    //System.out.println(u.getEmail());
                    //System.out.println(userList.get(0).getHumanProfile().getname());
                    resultsRecycler = (RecyclerView) getView().findViewById(R.id.RecyclerViewSearchMain);
                    adapter = new SearchResultsAdapter(getActivity(), userList);
                    resultsRecycler.setAdapter(adapter);
                    //here the second attribute for GridLayoutManager is 2 because this is the amount of columns we will have in the recycler view
                    resultsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("SearchActivityMain", error.getMessage());
            }
        };
        userRef.addListenerForSingleValueEvent(listener);

        advancedSearch = getView().findViewById(R.id.AdvancedSearchButton);
        advancedSearch.setOnClickListener(this);

        searchBar = getView().findViewById(R.id.SearchBar);
        searchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    searchByName(searchBar.getText().toString());
                    return true;
                }
                return false;
            }
        });
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

    public void searchByName(String searchName) {

        //String testName = "Cody";
        //users

        final String name = searchName.toLowerCase();
        final ArrayList<User> allUsers = new ArrayList<User>();
        final ArrayList<User> matchedUsers = new ArrayList<User>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot userSnapshot: snapshot.getChildren())
                {
                    allUsers.add(userSnapshot.getValue(User.class));
                    for(int i = 0; i<allUsers.size();i++)
                    {
                        if (allUsers.get(i).getHumanProfile().getname().toLowerCase().equals(name))
                        {
                            matchedUsers.add(allUsers.get(i));
                        }
                    }
                    //send values to another activity or screen
                    Intent intent = new Intent(getContext(), SearchResultsActivity.class);
                    intent.putExtra("SORTED_RESULTS", matchedUsers);

                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("SearchActivityMain", "loadPost:onCancelled", error.toException());
            }
        });


    }
    @Override
    public void onClick(View v)
    {
        if(v == advancedSearch)
        {
            startActivity(new Intent(getActivity(), AdvancedSearchActivity.class));
        }
    }

}