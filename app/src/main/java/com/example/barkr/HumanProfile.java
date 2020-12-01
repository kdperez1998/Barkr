package com.example.barkr;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HumanProfile  implements java.io.Serializable{
    private String name, gender, location, phoneNumber, bio, DOB;


    public HumanProfile()
    {
        name = null;
        gender = null;
        location = null;
        phoneNumber = null;
        bio = null;
        DOB = null;
    }

    public HumanProfile(String setName, String setGender, String setLocation,
                        String setPhoneNumber, String setBio, String setDOB)
    {
        name = setName;
        gender = setGender;
        location = setLocation;
        phoneNumber = setPhoneNumber;
        bio = setBio;
        DOB = setDOB;

    }

    public void setname(String name)
    {
        this.name = name;
    }

    public void setgender(String gender)
    {
        this.gender = gender;
    }

    public void setlocation(String location)
    {
        this.location = location;
    }

    public void setphoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setbio(String bio)
    {
        this.bio = bio;
    }

    public void setDOB(int month, int day, int year)
    {
        this.DOB = Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year);
    }

    public void setDOB(String dob)
    {
        this.DOB = dob;
    }

    public String getname()
    {
        return this.name;
    }

    public String getgender()
    {
        return this.gender;
    }

    public String getlocation()
    {
        return this.location;
    }

    public String getphoneNumber()
    {
        return this.phoneNumber;
    }

    public String getbio()
    {
        return this.bio;
    }

    public String getDOB()
    {
        return this.DOB;
    }

    public String getState()
    {
        String[] location = getlocation().split(", ");
        return location[1];
    }

    public String getCity()
    {
        String[] location = getlocation().split(", ");
        return location[0];
    }

    public int getDOBMonth()
    {
        String[] birthday = getDOB().split( "/");
        int month = Integer.parseInt(birthday[0]);
        return month;
    }

    public int getDOBDay()
    {
        String[] birthday = getDOB().split( "/");
        int day = Integer.parseInt(birthday[1]);
        return day;
    }

    public int getDOBYear()
    {
        String[] birthday = getDOB().split( "/");
        int year = Integer.parseInt(birthday[2]);
        return year;
    }

    public int getage()
    {
        int age = 0;
        //parse out DOB to month, day, and year values
        //String[] birthday = getDOB().split( "/");
        //int day = Integer.parseInt(birthday[1]);
        //int month = Integer.parseInt(birthday[0]);
        //int year = Integer.parseInt(birthday[2]);
        String dob = getDOB();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = formatter.parse(dob);
            Instant instant = date.toInstant();
            ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
            LocalDate givenDate = zone.toLocalDate();
            age = (int) ChronoUnit.YEARS.between(givenDate, LocalDate.now());

        }
        catch(Exception e)
        {
            Log.d("DogProfileGetAgeYears", e.toString());
        }
        //LocalDate l = LocalDate.of(year, month, day);
        //LocalDate now = LocalDate.now();
        //Period diff = Period.between(l, now);
        //return diff.getYears();
        return age;
    }

}