package com.example.barkr;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HumanProfile {
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
        /*
        String[] birthday = getDOB().split( "/");
        int year = Integer.parseInt(birthday[2]);
        return year;

         */
        return 0;
    }

    public int getage()
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
        //return diff.getYears();

         */
        return 0;
    }

}