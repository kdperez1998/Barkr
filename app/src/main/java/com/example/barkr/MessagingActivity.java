package com.example.barkr;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessagingActivity extends AppCompatActivity implements View.OnClickListener{

    User userValue;
    FirebaseUser currUser;
    FloatingActionButton sendButton;
    EditText input;
    ListView listOfMessages;
    Toolbar toolbar;
    DatabaseReference ref;

    private FirebaseListAdapter<ChatMessage> adapter;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.messageToolbar);
        setSupportActionBar(toolbar);

        sendButton = findViewById(R.id.fab);
        sendButton.setOnClickListener(this);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //get users in conversation
        currUser = FirebaseAuth.getInstance().getCurrentUser();
        if(getIntent().getExtras() != null)
        {
            userValue = (User) getIntent().getSerializableExtra("USER_PROFILE");
        }

        input = findViewById(R.id.input);

        listOfMessages = findViewById(R.id.list_of_messages);
        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class, R.layout.message, FirebaseDatabase.getInstance().getReference().child("users").child(currUser.getUid()).child("messages").child(userValue.getUserId())) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                //get references to views of message.xml
                TextView messageTextLeft = (TextView)v.findViewById(R.id.message_text);
                TextView messageUserLeft = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageTextLeft.setText(model.getMessageText());
                messageUserLeft.setText(model.getMessageUser());
                // Format the date before showing it
                SimpleDateFormat date = new SimpleDateFormat("MM-dd-yy (HH:mm:ss");
                messageTime.setText(date.format(model.getMessageTime()));
            }
        };
        listOfMessages.setAdapter(adapter);

    }

    @Override
    public void onClick(View v)
    {
        if(v == sendButton)
        {
            //create new message object and push it to both of the users messages reference
            ref = FirebaseDatabase.getInstance().getReference().child("users");
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        if(snapshot.child(currUser.getUid()).child("messages").hasChildren()){
                            User u = snapshot.child(currUser.getUid()).getValue(User.class);
                            ChatMessage message = new ChatMessage(input.getText().toString(), u.getHumanProfile().getname());
                            ref.child(userValue.getUserId()).child("messages").child(currUser.getUid()).push().setValue(message);
                            ref.child(currUser.getUid()).child("messages").child(userValue.getUserId()).push().setValue(message);

                            //clear input
                            input.setText("");
                        }
                        //TODO put that no messages exist
                        else
                        {

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    //TODO
                }
            };
            ref.addListenerForSingleValueEvent(listener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
