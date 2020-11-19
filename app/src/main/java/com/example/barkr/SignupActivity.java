package com.example.barkr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    //private FirebaseAuth mAuth;
    EditText username, password, reenterPassword, email;
    TextView signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signIn = findViewById(R.id.signinText);
        signIn.setOnClickListener(this);
        //mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        //check if user is signed in
        //FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    private void signUp()
    {

    }

    @Override
    public void onClick(View v)
    {
        if(v == signIn)
        {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        }
    }
}
