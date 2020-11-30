package com.example.barkr;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DogProfile  implements java.io.Serializable{
    private String name, breed, gender, bio, DOB;
    private boolean spayedNeutered, shotUpToDate;

    public DogProfile()
    {
        name = null;
        breed = null;
        gender = null;
        bio = null;
        DOB = null;
        spayedNeutered = false;
        shotUpToDate = false;
    }
    public DogProfile (String setName, String setBreed, String setGender, boolean setSpayedNeutered,
                       boolean setShotUpToDate, String setBio, String setDOB)
    {
        name = setName;
        breed = setBreed;
        gender = setGender;
        spayedNeutered = setSpayedNeutered;
        shotUpToDate = setShotUpToDate;
        bio = setBio;
        DOB = setDOB;

    }

    public void setname(String name)
    {
        this.name = name;
    }

    public void setbreed(String breed)
    {
        this.breed = breed;
    }

    public void setgender(String gender)
    {
        this.gender = gender;
    }

    public void setspayedNeutered(boolean spayedNeutered)
    {
        this.spayedNeutered = spayedNeutered;
    }

    public void setshotDate(boolean setShotUpToDate)
    {
        this.shotUpToDate = setShotUpToDate;
    }

    public void setbio(String bio)
    {
        this.bio = bio;
    }

    public void setDOB(String dob)
    {
        this.DOB = dob;
    }

    public void setDOB(int month, int day, int year)
    {
        this.DOB = Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year);
    }

    public String getname()
    {
        return this.name;
    }

    public String getbreed()
    {
        return this.breed;
    }

    public String getgender()
    {
        return this.gender;
    }

    public boolean getspayedNeutered()
    {
        return this.spayedNeutered;
    }

    public boolean getShotUpToDate()
    {
        return this.shotUpToDate;
    }

    public String getbio()
    {
        return this.bio;
    }

    public String getDOB()
    {
        return this.DOB;
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

    public int getAgeYears()
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
            Period period = Period.between(givenDate, LocalDate.now());
            age = period.getYears();
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

    public int getAgeDays()
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
            age = (int) ChronoUnit.DAYS.between(givenDate, LocalDate.now());

        }
        catch(Exception e)
        {
            Log.d("DogProfileGetAgeDays", e.toString());
        }
        //LocalDate l = LocalDate.of(year, month, day);
        //LocalDate now = LocalDate.now();
        //Period diff = Period.between(l, now);
        //return diff.getYears();
        return age;
    }

    public int getAgeMonths()
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
            Period period = Period.between(givenDate, LocalDate.now());
            age = period.getMonths();
        }
        catch(Exception e)
        {
            Log.d("DogProfileGetAgeMonths", e.toString());
        }
        //LocalDate l = LocalDate.of(year, month, day);
        //LocalDate now = LocalDate.now();
        //Period diff = Period.between(l, now);
        //return diff.getYears();
        return age;
    }

}