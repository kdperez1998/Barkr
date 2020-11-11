package com.example.barkr;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


public class EditProfile extends AppCompatActivity {
    EditText TextPostalAddress,editTextDate,TextPersonName,EditCity, EditEmail,editTextTextPersonName2,editTextTextPersonName1;
    ImageView profileImage,imageView3;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        profileImage = findViewById(R.id.imageView2);
        profileImage.setVisibility(View.VISIBLE);

    }

}