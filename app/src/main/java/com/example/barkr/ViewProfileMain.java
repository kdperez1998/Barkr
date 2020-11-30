package com.example.barkr;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewProfileMain extends Fragment implements View.OnClickListener
{
    private FirebaseDatabase database;
    private FirebaseUser user;
    DatabaseReference userRef;
    User userValue;
    TextView textHumanName, textHumanGenderAge, textLocation, textContact, textNumberPets, textHumanBio, textDistance,
        textPetName, textPetGenderAge, textPetBreed, textSpayedNeutered, textShotsUpToDate, textPetBio;
    FloatingActionButton editProfile, moreOptions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_own_profile, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //initialize human views
        //TODO add distance away textViews
        textHumanName = getView().findViewById(R.id.TextViewUser);
        textHumanGenderAge = getView().findViewById(R.id.UsersGenderAge);
        textLocation = getView().findViewById(R.id.TextDisplaysUsersCityState);
        textContact = getView().findViewById(R.id.TextDisplaysContact);
        textNumberPets = getView().findViewById(R.id.TextdisplaysNumofPets);
        textHumanBio = getView().findViewById(R.id.TextDislaysOwnersBio);

        //initialize dog views
        textPetName = getView().findViewById(R.id.TextdiplaysPetsName);
        textPetGenderAge = getView().findViewById(R.id.TextDisplaysPetsGenderAge);
        textPetBreed = getView().findViewById(R.id.TextDisplaysBreed);
        textSpayedNeutered = getView().findViewById(R.id.TextDisplaysSpayedNeutered);
        textShotsUpToDate = getView().findViewById(R.id.TextDisplaysShotsUpToDate);
        textPetBio = getView().findViewById(R.id.textDisplaysBio);

        editProfile = getView().findViewById(R.id.editProfileButton);
        editProfile.setOnClickListener(this);

        moreOptions = getView().findViewById(R.id.moreOptionsButton);
        moreOptions.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference().child("users").child(user.getUid());
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    userValue = snapshot.getValue(User.class);
                    HumanProfile hp = userValue.getHumanProfile();
                    DogProfile dp = userValue.getDogProfiles().get(0);

                    //set text for human values
                    textHumanName.setText(hp.getname());
                    if(hp.getgender().equals("f")) {
                        textHumanGenderAge.setText("Female, " + hp.getage());
                    }
                    else if(hp.getgender().equals("m"))
                    {
                        textHumanGenderAge.setText("Male, " + hp.getage());
                    }
                    textLocation.setText(hp.getlocation());
                    textContact.setText(user.getEmail() + "\n" + hp.getphoneNumber());
                    textNumberPets.setText("1 " + dp.getbreed());
                    textHumanBio.setText(hp.getbio());

                    //set text for dog values
                    textPetName.setText(dp.getname());
                    System.out.println(dp.getgender());
                    if(dp.getgender().equals("f")) {
                        if(dp.getAgeYears() == 0 && dp.getAgeMonths() == 0 && dp.getAgeDays() != 0) {
                            System.out.println("Female, " + dp.getAgeDays() + " days");
                            textPetGenderAge.setText("Female, " + dp.getAgeDays() + " days");
                        }
                        else if(dp.getAgeYears() == 0 && dp.getAgeMonths() != 0)
                        {
                            System.out.println("Female, " + dp.getAgeMonths() + " months");
                            textPetGenderAge.setText("Female, " + dp.getAgeMonths() + " months");
                        }
                        else if(dp.getAgeYears() != 0 && dp.getAgeMonths() != 0 && dp.getAgeDays() != 0)
                        {
                            System.out.println("Female, " + dp.getAgeYears() + " years");
                            textPetGenderAge.setText("Female, " + dp.getAgeYears() + " years");
                        }
                    }
                    else if(dp.getgender().equals("m"))
                    {
                        if(dp.getAgeYears() == 0 && dp.getAgeMonths() == 0 && dp.getAgeDays() != 0) {
                            System.out.println("Male, " + dp.getAgeDays() + " days");
                            textPetGenderAge.setText("Male>, " + dp.getAgeDays() + " days");
                        }
                        else if(dp.getAgeYears() == 0 && dp.getAgeMonths() != 0 && dp.getAgeDays() != 0)
                        {
                            System.out.println("Male, " + dp.getAgeMonths() + " months");
                            textPetGenderAge.setText("Male, " + dp.getAgeMonths() + " months");
                        }
                        else if(dp.getAgeYears() != 0 && dp.getAgeMonths() != 0 && dp.getAgeDays() != 0)
                        {
                            System.out.println("Male, " + dp.getAgeYears() + " years");
                            textPetGenderAge.setText("Male, " + dp.getAgeYears() + " years");
                        }
                    }
                    textPetBreed.setText(dp.getbreed());
                    if(dp.getShotUpToDate()) {
                        textShotsUpToDate.setText("Yes");
                    }
                    else
                    {
                        textShotsUpToDate.setText("No");
                    }
                    if(dp.getspayedNeutered()) {
                        textSpayedNeutered.setText("Yes");
                    }
                    else
                    {
                        textSpayedNeutered.setText("No");
                    }
                    textPetBio.setText(dp.getbio());
                }
                else
                {
                    startActivity(new Intent(getContext(), CreateProfileActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        userRef.addListenerForSingleValueEvent(listener);
    }

    @Override
    public void onClick(View v)
    {
        if(v == editProfile)
        {
            startActivity(new Intent(getActivity(), EditProfileActivity.class));
        }
        if(v == moreOptions)
        {
            showMenu(v);
        }
    }

    private void showMenu(View v)
    {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.more_options_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.back:
                        //TODO ???
                        return true;
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        return true;
                    case R.id.changeEmail:

                        return true;
                    case R.id.changePassword:
                        FirebaseAuth.getInstance().sendPasswordResetEmail(user.getEmail()).addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getActivity(), "Reset password link sent to " +
                                                    user.getEmail(),
                                            Toast.LENGTH_LONG).show();
                                    System.out.println("Reset password link sent to " + user.getEmail());
                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "Email failed to send.",
                                            Toast.LENGTH_LONG).show();
                                    System.out.println("Email failed to send.");
                                }
                            }});
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }
}