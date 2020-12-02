package com.example.barkr;

import android.media.Image;

import java.util.ArrayList;

public class User implements java.io.Serializable
{
    private HumanProfile humanProfile;
    private ArrayList<DogProfile> dogProfiles;
    //ArrayList<User> favorites;
    private String email;
    String userId;
    Image profilePicture;

    public User(String email, HumanProfile hp, ArrayList<DogProfile> dp, String uid)
    {
        this.setHumanProfile(hp);
        this.setDogProfiles(dp);
        this.setEmail(email);
        this.setUserId(uid);
        //this.favorites = new ArrayList<User>();
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

    /*public void setFavorites(ArrayList<User> f) {
        this.favorites = f;
    }*/
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
            else if(range.length != 1)
            {
                if((dogProfile.getAgeMonths() >= Integer.parseInt(range[0])) && (dogProfile.getAgeMonths() <= Integer.parseInt(range[1])))
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
            else if(range.length != 1)
            {
                if((dogProfile.getAgeDays() >= Integer.parseInt(range[0])) && (dogProfile.getAgeDays() <= Integer.parseInt(range[1])))
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
            else if(range.length != 1)
            {
                if((dogProfile.getAgeYears() >= Integer.parseInt(range[0])) && (dogProfile.getAgeYears() <= Integer.parseInt(range[1])))
                {
                    returnValue += 1;
                }
            }
        }

        //compare dog gender selection
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
        String rangeHuman[] = f.getHumanAge().split("-");
        if(rangeHuman.length == 1 && !(rangeHuman[0].equals("")))
        {
            if(Integer.parseInt(f.getHumanAge()) == humanProfile.getage())
            {
                returnValue += 1;
            }
        }
        else if(rangeHuman.length != 1)
        {
            if((humanProfile.getage() >= Integer.parseInt(rangeHuman[0])) && (humanProfile.getage() <= Integer.parseInt(rangeHuman[1])))
            {
                returnValue += 1;
            }
        }

        //compare human gender selection
        ArrayList<String> genderHuman = f.getDogGender();

        if(genderHuman.size() == 1)
        {
            if(genderHuman.get(0).equals(humanProfile.getgender()))
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
    /*
    public ArrayList<User> getFavorites()
    {
        return favorites;
    }

    public void addUserToFavorites(User u)
    {
        favorites.add(u);
    }

    public void removeUserFromFavorites(User u)
    {
        ArrayList<User> newFavoritesList = new ArrayList<User>();
        for(User f : favorites)
        {
            if(f.getUserId() != u.getUserId())
            {
                newFavoritesList.add(f);
            }
        }
        this.setFavorites(newFavoritesList);
    }*/

}