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

public class SignupActivity extends AppCompatActivity implements View.OnClickListener
{

    private FirebaseAuth mAuth;
    EditText username, password, reenterPassword, email;
    TextView signIn;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signIn = findViewById(R.id.signinText);
        signIn.setOnClickListener(this);

        //username = findViewById(R.id.usernameInput);
        password = findViewById(R.id.passwordInput);
        reenterPassword = findViewById(R.id.reenterPasswordInput);
        email = findViewById(R.id.emailInput);

        signUpButton = findViewById(R.id.signupButton);
        signUpButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        //check if user is signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }

    private void signUp()
    {
        if(TextUtils.isEmpty(email.getText().toString()))
        {
            email.setError("This field can not be blank");
        }
        if(TextUtils.isEmpty(password.getText().toString()))
        {
            password.setError("This field can not be blank");
        }
        if(TextUtils.isEmpty(reenterPassword.getText().toString()))
        {
            reenterPassword.setError("This field can not be blank");
        }
        if(TextUtils.isEmpty(username.getText().toString()))
        {
            username.setError("This field can not be blank");
        }
        if(!(TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString()) ||
                TextUtils.isEmpty(reenterPassword.getText().toString()) || TextUtils.isEmpty(email.getText().toString())))
        {
            if(password.getText().toString().equals(reenterPassword.getText().toString())) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(!task.isSuccessful()) {
                                    /*try {

                                    }
                                    catch(FirebaseAuthWeakPasswordException weakPassword)
                                    {
                                        password.setError("Weak password!");
                                    }
                                    catch(FirebaseAuthUserCollisionException existEmail)
                                    {
                                        email.setError("Email already associated with an account");
                                    }
                                    */
                                    // If sign in fails, display a message to the user

                                    Log.w("SignUpEmailPassword", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignupActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                if (task.isSuccessful())
                                {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("SignUpEmailPassword", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startActivity(new Intent(SignupActivity.this, CreateProfileActivity.class));
                                }
                            }
                        });
            }

            else
            {
                reenterPassword.setError("Passwords do not match");
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        if(v == signIn)
        {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        }
        if(v == signUpButton)
        {
            signUp();
        }
    }
}
