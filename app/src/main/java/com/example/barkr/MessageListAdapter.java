package com.example.barkr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder>
{

    FirebaseUser user;
    private ArrayList<String> idList;
    private LayoutInflater mInflater;
    Context c;

    //data passed into constructor
    public MessageListAdapter(Context context, ArrayList<String> data)
    {
        user = FirebaseAuth.getInstance().getCurrentUser();
        this.mInflater = LayoutInflater.from(context);
        this.idList = data;
        c = context;
    }

    //inflates the row layout from xml when needed
    @Override
    public MessageListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.message_list_row, parent, false);
        return new MessageListAdapter.ViewHolder(view);
    }

    //binds data to the text view in each row
    @Override
    public void onBindViewHolder(MessageListAdapter.ViewHolder holder, int position)
    {
        //get the user and the last message sent from the database with the info at idList.get(position)
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("users");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User currUser = snapshot.child(user.getUid()).getValue(User.class);
                    User showUser = snapshot.child(idList.get(position)).getValue(User.class);
                    if(snapshot.child(user.getUid()).child("messages").child(idList.get(position)).exists()) {
                        ChatMessage lastMessageSent = snapshot.child(user.getUid()).child("messages").child(idList.get(position)).getValue(ChatMessage.class);
                        String name = showUser.getHumanProfile().getname();
                        String lastMessageSentString = lastMessageSent.getMessageText();
                        holder.profileName.setText(name);
                        holder.message.setText(lastMessageSentString);
                        /*if (lastMessageSent.getMessageUser().equals(showUser.getHumanProfile().getname())) {
                            holder.message.setTextColor(Color.parseColor("#FF6565"));
                        }*/
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("MessageList", error.getMessage());
            }
        };
        usersRef.addListenerForSingleValueEvent(listener);
    }

    //get length of array list
    public int getItemCount()
    {
        return idList.size();
    }

    //stores and recycles views as they are scrolled off the screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView profileName, message;
        public FloatingActionButton favoriteButton, moreButton;

        //constructor
        public ViewHolder(View itemView)
        {
            super(itemView);

            //set on click listener for the current row in the recycler view
            itemView.setOnClickListener(this);

            profileName = (TextView) itemView.findViewById(R.id.ProfileName);
            message = (TextView) itemView.findViewById(R.id.MessageText);

            favoriteButton = (FloatingActionButton) itemView.findViewById(R.id.FavoriteButton);
            favoriteButton.setOnClickListener(this);

            moreButton = (FloatingActionButton) itemView.findViewById(R.id.MoreButton);
            moreButton.setOnClickListener(this);
        }

        //when an element is clicked on
        @Override
        public void onClick(View v)
        {
            if(v == itemView)
            {
                //load the viewProfileActivity with user clicked on
                Intent intent = new Intent(c, MessagingActivity.class);
                intent.putExtra("USER_PROFILE", getItem(getPosition()));

                c.startActivity(intent);

            }
        }

    }

    String getItem(int id)
    {
        return idList.get(id);
    }
}