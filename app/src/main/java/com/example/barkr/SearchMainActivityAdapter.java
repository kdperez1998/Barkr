package com.example.barkr;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchMainActivityAdapter extends RecyclerView.Adapter<SearchMainActivityAdapter.ViewHolder> {

    private ArrayList<User> userList;
    private LayoutInflater mInflater;
    Context c;

    //data passed into constructor
    SearchMainActivityAdapter(Context context, ArrayList<User> data)
    {
        this.mInflater = LayoutInflater.from(context);
        this.userList = data;
        c = context;
    }

    //inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.main_search_row, parent, false);
        return new ViewHolder(view);
    }

    //binds data to the text view in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        User u = userList.get(position);
        String name = u.getHumanProfile().getname();
        String dogAmount = u.getDogProfiles().size() + " Dogs";
        holder.profileName.setText(name);
        holder.amountDogs.setText(dogAmount);
    }

    //get length of array list
    public int getItemCount()
    {
        return userList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView profileName, amountDogs;
        public ImageButton favoriteButton;
        public ImageView profilePicture;

        //constructor
        public ViewHolder(View itemView)
        {
            super(itemView);

            profileName = (TextView) itemView.findViewById(R.id.ProfileName);
            amountDogs = (TextView) itemView.findViewById(R.id.DogAmount);
            favoriteButton = (ImageButton) itemView.findViewById(R.id.favoriteButton);
            favoriteButton.setOnClickListener(this);
            profilePicture = (ImageView) itemView.findViewById(R.id.ProfilePicture);

            //set on click listener for the current row in the recycler view
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v)
        {
            if(v == itemView)
            {
                //load viewprofileactivity with user clicked on
                Intent intent = new Intent(c, ViewProfileActivity.class);
                intent.putExtra("USER_PROFILE", getItem(getPosition()));

                c.startActivity(intent);

            }
            if(v == favoriteButton)
            {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("favorites");
                ValueEventListener listener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<User> newFavList = new ArrayList<User>();
                        boolean isNew = true;
                        for(DataSnapshot ds : snapshot.getChildren())
                        {
                            User currUser = ds.getValue(User.class);
                            if(!currUser.getUserId().equals(userList.get(getPosition()).getUserId()))
                            {
                                newFavList.add(currUser);
                            }
                            else {
                                isNew = false;
                            }
                        }
                        if(isNew) {
                            newFavList.add(userList.get(getPosition()));
                        }
                        ref.setValue(newFavList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //TODO
                    }
                };
                ref.addListenerForSingleValueEvent(listener);
            }
        }
    }

    User getItem(int id)
    {
        return userList.get(id);
    }
}