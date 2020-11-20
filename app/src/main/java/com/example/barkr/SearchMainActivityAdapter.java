package com.example.barkr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.main_search_row, parent, false);
        return new ViewHolder(view);
    }

    //binds data to the text view in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView profileName, amountDogs;
        public ImageButton favoriteButton;
        public ImageView profilePicture;

        //constructor
        public ViewHolder(View itemView) {
            super(itemView);

            profileName = (TextView) itemView.findViewById(R.id.ProfileName);
            amountDogs = (TextView) itemView.findViewById(R.id.DogAmount);
            favoriteButton = (ImageButton) itemView.findViewById(R.id.FavoriteButton);
            favoriteButton.setOnClickListener(this);
            profilePicture = (ImageView) itemView.findViewById(R.id.ProfilePicture);
            profilePicture.setOnClickListener(this);

            //set on click listener for the current row in the recycler view
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v)
        {

        }
    }

    User getItem(int id)
    {
        return userList.get(id);
    }
}