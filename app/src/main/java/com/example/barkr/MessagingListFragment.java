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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessagingListFragment extends Fragment implements View.OnClickListener {

    RecyclerView messagingRecycler;
    ArrayList<String> idList;
    static MessageListAdapter adapter;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.messaging_list_frag, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        idList = new ArrayList<String>();

        user = FirebaseAuth.getInstance().getCurrentUser();

        //System.out.println(user.getUid());
        //HumanProfile hp = new HumanProfile("name", "f", "Tyler, Texas", "903372", "hello", "12/12/1222");
        //DogProfile dogProfile = new DogProfile("pet", "corgi", "f", false, false, "hello", "12/12/1212");
        //ArrayList<DogProfile> dp = new ArrayList<DogProfile>();
        //dp.add(dogProfile);
        //userList.add(new User(user.getEmail(), hp, dp, user.getUid()));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("users").child(user.getUid()).child("messages");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()) {
                    for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                        idList.add(messageSnapshot.getKey());
                    }

                    messagingRecycler = (RecyclerView) getView().findViewById(R.id.RecyclerViewMessageList);
                    adapter = new MessageListAdapter(getActivity(), idList);
                    messagingRecycler.setAdapter(adapter);
                    messagingRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                }
                //TODO display that there are no messages
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("MessagingListFragment", "loadPost:onCancelled", error.toException());
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}