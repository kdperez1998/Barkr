package com.example.barkr;

import java.util.ArrayList;

public class User
{
    private HumanProfile humanProfile;
    private ArrayList<DogProfile> dogProfiles;
    private String email;
    String userId;

    public User(String email, HumanProfile hp, ArrayList<DogProfile> dp, String uid)
    {
        this.setHumanProfile(hp);
        this.setDogProfiles(dp);
        this.setEmail(email);
        this.setUserId(uid);
    }

    public User()
    {

    }

    public void setEmail(String e)
    {
        this.email = e;
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setHumanProfile(HumanProfile hp)
    {
        humanProfile = hp;
        //humanProfile = new HumanProfile(hp.getname(), hp.getgender(), hp.getlocation(), hp.getphoneNumber(), hp.getemail(), hp.getbio(), hp.getage());
    }

    public void setDogProfiles(ArrayList<DogProfile> dp)
    {
        dogProfiles = new ArrayList<DogProfile>();
        for(int i = 0; i < dp.size(); i++)
        {
            //DogProfile newDogProfile = new DogProfile(dp.get(i).getname(), dp.get(i).getbreed(), dp.get(i).getgender(), dp.get(i).getspayedNeutered(), dp.get(i).getShotUpToDate(), dp.get(i).getbio(), dp.get(i).getage());
            //dogProfiles.add(newDogProfile);
            dogProfiles.add(dp.get(i));
        }

    }

    public void setUserId(String uid)
    {
        this.userId = uid;
    }

    public int numValuesMatch(Filter f)
    {
        int returnValue = 0;

        //compare dog values
        //currently we only have one dog value available per user, so get that single one and compare values
        DogProfile dogProfile = dogProfiles.get(0);
        if(f.getDogAgeDesc().equals("months"))
        {
            String range[] = f.getDogAge().split("-");
            if(range.length == 1 && !(range[0].equals("")))
            {
                if(Integer.parseInt(f.getDogAge()) == dogProfile.getAgeMonths())
                {
                    returnValue += 1;
                }
            }
            else if(range.length == 2)
            {
                if((dogProfile.getAgeMonths() <= Integer.parseInt(range[1])) && (dogProfile.getAgeMonths() >= Integer.parseInt(range[0])))
                {
                    returnValue += 1;
                }
            }
        }
        else if(f.getDogAgeDesc().equals("days"))
        {
            String range[] = f.getDogAge().split("-");
            if(range.length == 1 && !(range[0].equals("")))
            {
                if(Integer.parseInt(f.getDogAge()) == dogProfile.getAgeDays())
                {
                    returnValue += 1;
                }
            }
            else if(range.length == 2)
            {
                if((dogProfile.getAgeDays() <= Integer.parseInt(range[1])) && (dogProfile.getAgeDays() >= Integer.parseInt(range[0])))
                {
                    returnValue += 1;
                }
            }
        }
        else if(f.getDogAgeDesc().equals("years"))
        {
            String range[] = f.getDogAge().split("-");
            if(range.length == 1 && !(range[0].equals("")))
            {
                if(Integer.parseInt(f.getDogAge()) == dogProfile.getAgeYears())
                {
                    returnValue += 1;
                }
            }
            else if(range.length == 2)
            {
                if((dogProfile.getAgeYears() <= Integer.parseInt(range[1])) && (dogProfile.getAgeYears() >= Integer.parseInt(range[0])))
                {
                    returnValue += 1;
                }
            }
        }

        //compare gender selection
        ArrayList<String> genderDog = f.getDogGender();
        if(genderDog.size() == 1)
        {
            if(genderDog.get(0).equals(dogProfile.getgender()))
            {
                returnValue += 1;
            }
        }

        //compare if spayed or neutered
        //if filter value is false, they have no preference, so only check the values if the filter value is true
        if(f.getSpayedNeutered() == true && dogProfile.getspayedNeutered() == f.getSpayedNeutered())
        {
            returnValue += 1;
        }

        //compare if dog shots are up to date
        //if filter value is false, they have no preference, so only check the values if the filter value is true
        if(f.getShotsUpToDate() == true && dogProfile.getShotUpToDate() == f.getShotsUpToDate())
        {
            returnValue += 1;
        }

        //compare breed
        //make sure the value isnt the default value and then check to see if the breeds are matching
        if(!f.getBreed().equals("") && f.getBreed().equals(dogProfile.getbreed()))
        {
            returnValue += 1;
        }


        //compare human values
        String range[] = f.getHumanAge().split("-");
        if(range.length == 1 && humanProfile.getage() == Integer.parseInt(range[0]))
        {
            returnValue += 1;
        }
        else if(range.length == 2)
        {
            if(humanProfile.getage() >= Integer.parseInt(range[0]) && humanProfile.getage() <= Integer.parseInt(range[1])) {
                returnValue += 1;
            }
        }

        //compare gender selection
        ArrayList<String> genderHuman = f.getDogGender();
        if(genderHuman.size() == 1)
        {
            if(genderHuman.get(0).equals(dogProfile.getgender()))
            {
                returnValue += 1;
            }
        }

        return returnValue;
    }

    public HumanProfile getHumanProfile()
    {
        return humanProfile;
    }

    public ArrayList<DogProfile> getDogProfiles()
    {
        return dogProfiles;
    }

    public String getUserId()
    {
        return userId;
    }
}