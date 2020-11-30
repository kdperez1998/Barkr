package com.example.barkr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
                //load the viewProfileActivity
            }
        }

    }

    User getItem(int id)
    {
        return resultsList.get(id);
    }
}
