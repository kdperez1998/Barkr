package com.example.barkr;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import java.util.concurrent.atomic.AtomicInteger;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>
{

    private ArrayList<User> resultsList;
    private LayoutInflater mInflater;
    Context c;

    //data passed into constructor
    public SearchResultsAdapter(Context context, ArrayList<User> data)
    {
        this.mInflater = LayoutInflater.from(context);
        this.resultsList = data;
        c = context;
    }

    //inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.search_results_row, parent, false);
        return new ViewHolder(view);
    }

    //binds data to the text view in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        User u = resultsList.get(position);
        String name = u.getHumanProfile().getname();

        String dogAmount = u.getDogProfiles().size() + " " + u.getDogProfiles().get(0).getbreed();

        if(u.getDogProfiles().size() >= 2 || u.getDogProfiles().size() < 1)
        {
            dogAmount += "s";
        }
        holder.profileName.setText(name);
        holder.amountDogs.setText(dogAmount);
    }

    //get length of array list
    public int getItemCount()
    {
        return resultsList.size();
    }

    //stores and recycles views as they are scrolled off the screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView profileName, amountDogs;
        public ImageButton favoriteButton, messageButton;

        //constructor
        public ViewHolder(View itemView)
        {
            super(itemView);

            //set on click listener for the current row in the recycler view
            itemView.setOnClickListener(this);

            profileName = (TextView) itemView.findViewById(R.id.ProfileName);
            amountDogs = (TextView) itemView.findViewById(R.id.DogAmount);

            favoriteButton = (ImageButton) itemView.findViewById(R.id.FavoriteButton);
            favoriteButton.setOnClickListener(this);

            messageButton = (ImageButton) itemView.findViewById(R.id.MessagingButton);
            messageButton.setOnClickListener(this);
        }

        //when an element is clicked on
        @Override
        public void onClick(View v)
        {
            if(v == itemView)
            {
                //load the viewProfileActivity with user clicked on
                Intent intent = new Intent(c, ViewProfileActivity.class);
                intent.putExtra("USER_PROFILE", getItem(getPosition()));

                c.startActivity(intent);


            }
            if (v == messageButton) {
                Intent i = new Intent(c, MessagingActivity.class);
                i.putExtra("USER_PROFILE", getItem(getPosition()));
                c.startActivity(i);
            }
            if(v == favoriteButton)
            {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("favorites");
                ValueEventListener listener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<User> newFavList = new ArrayList<User>();
                        boolean isNew = true;
                        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("favorites");
                        for(DataSnapshot ds : snapshot.getChildren())
                        {
                            User currUser = ds.getValue(User.class);
                            if(!currUser.getUserId().equals(resultsList.get(getPosition()).getUserId()))
                            {
                                newFavList.add(currUser);
                            }
                            else {
                                isNew = false;
                            }
                        }
                        if(isNew) {
                            newFavList.add(resultsList.get(getPosition()));
                        }
                        dRef.setValue(newFavList);
                        notifyDataSetChanged();


                }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //TODO
                    }
                };
                ref.addListenerForSingleValueEvent(listener);
            }
        }

        User getItem(int id)
        {
            return resultsList.get(id);
        }

    }


}
