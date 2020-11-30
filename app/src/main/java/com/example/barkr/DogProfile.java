package com.example.barkr;

import java.time.LocalDate;
import java.time.Period;

public class DogProfile {
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
        /*
        String[] birthday = getDOB().split( "/");
        int month = Integer.parseInt(birthday[0]);
        return month;

         */
        return 0;
    }

    public int getDOBDay()
    {
        /*
        String[] birthday = getDOB().split( "/");
        int day = Integer.parseInt(birthday[1]);
        return day;

         */
        return 0;
    }

    public int getDOBYear()
    {
        /*String[] birthday = getDOB().split( "/");
        int year = Integer.parseInt(birthday[2]);
        return year;
         */
        int year = 0;
        return year;
    }

    public int getAgeYears()
    {
        /*
        //parse out DOB to month, day, and year values
        String[] birthday = getDOB().split( "/");
        int day = Integer.parseInt(birthday[1]);
        int month = Integer.parseInt(birthday[0]);
        int year = Integer.parseInt(birthday[2]);

        LocalDate l = LocalDate.of(year, month, day);
        LocalDate now = LocalDate.now();
        Period diff = Period.between(l, now);
        return diff.getYears();

         */
        return 0;
    }

    public int getAgeDays()
    {
        /*
        //parse out DOB to month, day, and year values
        String[] birthday = getDOB().split( "/");
        int day = Integer.parseInt(birthday[1]);
        int month = Integer.parseInt(birthday[0]);
        int year = Integer.parseInt(birthday[2]);

        LocalDate l = LocalDate.of(year, month, day);
        LocalDate now = LocalDate.now();
        Period diff = Period.between(l, now);
        return diff.getDays();

         */
        return 0;
    }

    public int getAgeMonths()
    {
        /*
        //parse out DOB to month, day, and year values
        String[] birthday = getDOB().split( "/");
        int day = Integer.parseInt(birthday[1]);
        int month = Integer.parseInt(birthday[0]);
        int year = Integer.parseInt(birthday[2]);

        LocalDate l = LocalDate.of(year, month, day);
        LocalDate now = LocalDate.now();
        Period diff = Period.between(l, now);
        return diff.getMonths();

         */
        return 0;
    }

}