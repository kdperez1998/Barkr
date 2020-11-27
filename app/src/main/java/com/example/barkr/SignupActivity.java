package com.example.barkr;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    private void signUp()
    {
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            }
                            catch(FirebaseAuthWeakPasswordException weakPassword)
                            {
                                password.setError("Password not strong enough");
                                reenterPassword.setError("Password not string enough");
                            }
                            catch(FirebaseAuthUserCollisionException existEmail)
                            {
                                email.setError("This email is already associated with an account");
                            }
                            catch(Exception e)
                            {
                                /*if(e.getMessage().equals("auth/invalid-email")) {
                                    email.setError("Please enter a valid email");
                                }
                                if(e.getMessage().equals("auth/email-already-in-use")) {
                                    email.setError("This email is already associated with an account");
                                }
                                if(e.getMessage().equals("auth/weak-password")) {
                                        password.setError("Password not strong enough");

                                }*/
                                //else
                                //{
                                    Toast.makeText(SignupActivity.this, "Authentication failed. " + task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                //}
                            }
                            // If sign in fails, display a message to the user

                            Log.w("SignUpEmailPassword", "createUserWithEmail:failure", task.getException());
                        }
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SignUpEmailPassword", "createUserWithEmail:success");
                            loginWithEmail(email.getText().toString(), password.getText().toString());
                        }
                    }
                });
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
            if(!(android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()))
            {
                email.setError("Invalid email format. Please enter a valid email");
            }
            if(!(TextUtils.isEmpty(password.getText().toString()) || TextUtils.isEmpty(reenterPassword.getText().toString()) || TextUtils.isEmpty(email.getText().toString()))) {
                if(password.getText().toString().equals(reenterPassword.getText().toString())) {
                    signUp();
                }
                else
                {
                    reenterPassword.setError("Passwords do not match");
                    password.setError("Passwords do not match");
                }
              }


        }
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
                    Log.d("EmailPassword", "signInWithEmail:success");
                    Toast.makeText(SignupActivity.this, "Authentication success.",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, CreateProfileActivity.class));
                }
                else
                {
                    //sign in fails, display error message to user
                    Log.w("EmailPassword", "signInWithEmail:failure", task.getException());
                    Toast.makeText(SignupActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
