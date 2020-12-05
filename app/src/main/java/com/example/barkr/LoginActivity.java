package com.example.barkr;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    TextView signUp, forgot;
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

        forgot = findViewById(R.id.forgotPasswordText);
        forgot.setOnClickListener(this);

        loginButton = findViewById(R.id.signInButton);
        loginButton.setOnClickListener(this);

        email = findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);
    }
/*
    @Override
    public void onStart()
    {
        super.onStart();
        //check if user is signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            //check if their profile is created in the database, if not, sent to the create activity page
            FirebaseUser user = mAuth.getCurrentUser();
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference userRef = rootRef.child("users").child(user.getUid());
            ValueEventListener listener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!snapshot.exists())
                    {
                        //send the user to the create profile screen so they may input their profile information
                        startActivity(new Intent(LoginActivity.this, CreateProfileActivity.class));
                    }
                    else
                    {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("EmailPassword", error.getMessage());
                }
            };
            userRef.addListenerForSingleValueEvent(listener);

        }

    }
*/
    private void loginWithEmail(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    //sign in success, move to the main application page with user information
                    FirebaseUser user = mAuth.getCurrentUser();
                    Log.d("EmailPassword", "signInWithEmail:success");
                    Toast.makeText(LoginActivity.this, "Authentication success.",
                            Toast.LENGTH_SHORT).show();

                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference userRef = rootRef.child("users").child(user.getUid());
                    ValueEventListener listener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.exists())
                            {
                                //send the user to the create profile screen so they may input their profile information
                                startActivity(new Intent(LoginActivity.this, CreateProfileActivity.class));
                            }
                            else
                            {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("EmailPassword", error.getMessage());
                        }
                    };
                    userRef.addListenerForSingleValueEvent(listener);
                    userRef.removeEventListener(listener);
                }
                else
                {
                    //sign in fails, display error message to user
                    Log.w("EmailPassword", "signInWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onClick(View v)
    {
        if (v == signUp)
        {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        }

        else if (v == loginButton)
        {
            if(TextUtils.isEmpty(email.getText().toString()))
            {
                email.setError("This field can not be blank");
            }

            if(TextUtils.isEmpty(password.getText().toString()))
            {
                password.setError("This field can not be blank");
            }

            if(!(TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString())))
            {
                loginWithEmail(email.getText().toString(), password.getText().toString());
            }

        }
        else if (v == forgot)
        {
            if (TextUtils.isEmpty(email.getText().toString()))
            {
                Toast.makeText(this, "Please enter email id", Toast.LENGTH_LONG).show();
            }
            else
                {
                    mAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(LoginActivity.this, "Reset password link sent to " +
                                            email.getText().toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Email failed to send.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }});
            }
        }
    }
}
