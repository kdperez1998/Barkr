package com.example.barkr;

import java.io.Serializable;
import java.util.ArrayList;

public class Filter implements Serializable {
    String dogAge, dogAgeDesc, breed, humanAge;
    boolean spayedNeutered, shotsUpToDate;
    ArrayList<String> dogGender, humanGender;
    int miles;

    public Filter()
    {
        dogAge = "";
        dogAgeDesc = "";
        breed = "";
        humanAge = "";
        dogGender = new ArrayList<String>();
        humanGender = new ArrayList<String>();
        spayedNeutered = false;
        shotsUpToDate = false;
        miles = 50;
    }

    public Filter(String setDogAge, String setDogAgeDescription, ArrayList<String> setDogGender, boolean setSpayedNeautered, boolean setShotsUpToDate,
                  String setBreed, String setHumanAge, ArrayList<String> setHumanGender, int setMiles)
    {
        dogAge = setDogAge;
        dogAgeDesc = setDogAgeDescription;
        spayedNeutered = setSpayedNeautered;
        shotsUpToDate = setShotsUpToDate;
        breed = setBreed;
        humanAge = setHumanAge;
        dogGender = new ArrayList<String>();
        humanGender = new ArrayList<String>();
        for(int i = 0; i < setDogGender.size(); i++)
        {
            dogGender.add(setDogGender.get(i));
        }
        for(int i = 0; i < setHumanGender.size(); i++)
        {
            humanGender.add(setHumanGender.get(i));
        }
        miles = setMiles;
    }

    public void setDogAge(String val)
    {
        dogAge = val;
    }
    public void setDogAgeDesc(String val)
    {
        dogAgeDesc = val;
    }
    public void setBreed(String val)
    {
        breed = val;
    }
    public void setHumanAge(String val)
    {
        humanAge = val;
    }
    public void setSpayedNeutered(boolean val)
    {
        spayedNeutered = val;
    }
    public void setShotsUpToDate(boolean val)
    {
        shotsUpToDate = val;
    }
    public void setDogGender(ArrayList<String> val)
    {
        dogGender = new ArrayList<String>();
        for(int i = 0; i < val.size(); i++)
        {
            dogGender.add(val.get(i));
        }
    }
    public void setHumanGender(ArrayList<String> val)
    {
        humanGender = new ArrayList<String>();
        for(int i = 0; i < val.size(); i++)
        {
            humanGender.add(val.get(i));
        }
    }
    public void setMiles(int setMiles)
    {
        miles = setMiles;
    }
    public String getDogAge()
    {
        return dogAge;
    }
    public String getHumanAge()
    {
        return humanAge;
    }
    public String getDogAgeDesc()
    {
        return dogAgeDesc;
    }
    public String getBreed()
    {
        return breed;
    }
    public boolean getSpayedNeutered()
    {
        return spayedNeutered;
    }
    public boolean getShotsUpToDate()
    {
        return shotsUpToDate;
    }

    public ArrayList<String> getDogGender()
    {
        return dogGender;
    }

    public ArrayList<String> getHumanGender()
    {
        return humanGender;
    }

    public int getMiles()
    {
        return miles;
    }

}
