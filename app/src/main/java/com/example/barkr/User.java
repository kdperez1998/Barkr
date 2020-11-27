package com.example.barkr;

import java.util.ArrayList;

public class User
{
    private HumanProfile humanProfile;
    private ArrayList<DogProfile> dogProfiles;
    private String email;

    public User(String email, HumanProfile hp, ArrayList<DogProfile> dp)
    {
        this.setHumanProfile(hp);
        this.setDogProfiles(dp);
        this.setEmail(email);
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
        //humanProfile = new HumanProfile(hp.getname(), hp.getgender(), hp.getlocation(), hp.getphoneNumber(), hp.getemail(), hp.getbio(), hp.getage());
    }

    public void setDogProfiles(ArrayList<DogProfile> dp)
    {
        dogProfiles = new ArrayList<DogProfile>();
        //dogProfiles.add(new DogProfile("Bagel", "Corgi", "m", false, true, "hello", 6));

        for(int i = 0; i < dp.size(); i++)
        {
            //DogProfile newDogProfile = new DogProfile(dp.get(i).getname(), dp.get(i).getbreed(), dp.get(i).getgender(), dp.get(i).getspayedNeutered(), dp.get(i).getShotUpToDate(), dp.get(i).getbio(), dp.get(i).getage());
            //dogProfiles.add(newDogProfile);
        }

    }

    public HumanProfile getHumanProfile()
    {
        return humanProfile;
    }

    public ArrayList<DogProfile> getDogProfiles()
    {
        return dogProfiles;
    }
}