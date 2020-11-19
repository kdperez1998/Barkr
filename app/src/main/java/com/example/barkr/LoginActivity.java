package com.example.barkr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    TextView signUp;
    public Button loginButton;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        signUp = findViewById(R.id.signupText);
        signUp.setOnClickListener(this);

        loginButton = findViewById(R.id.signInButton);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        //check if user is signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    private void loginWithEmail(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    //sign in success, move to the main application page with user information
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(LoginActivity.this, "Authentication success.",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //sign in fails, display error message to user
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        if(v == signUp)
        {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        }
        if(v == loginButton)
        {
            loginWithEmail(email.getText().toString(), password.getText().toString());
        }
    }
}
