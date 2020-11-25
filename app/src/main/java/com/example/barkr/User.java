package com.example.barkr;

import java.util.ArrayList;

public class User {
    private String userName, Password;
    private HumanProfile humanProfile;
    private ArrayList<DogProfile> dogProfiles;

    public User(String u)
    {
        this.userName = u;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
    public String getUsername() {
        return this.userName;
    }
    public String getPassword() {
        return this.Password;
    }

    public void setHumanProfile(HumanProfile hp)
    {
        humanProfile = new HumanProfile(hp.getname(), hp.getgender(), hp.getlocation(), hp.getphoneNumber(), hp.getemail(), hp.getbio(), hp.getage());
    }

    public void setDogProfiles()
    {
        dogProfiles = new ArrayList<DogProfile>();
        dogProfiles.add(new DogProfile("Bagel", "Corgi", "m", false, true, "hello", 6));

        for(int i = 0; i < dogProfiles.size(); i++)
        {
            dogProfiles.get(i).getname();
            dogProfiles.get(i).getbreed();
            dogProfiles.get(i).getgender();
            dogProfiles.get(i).getspayedNeutered();
            dogProfiles.get(i).getShotUpToDate();
            dogProfiles.get(i).getbio();
            dogProfiles.get(i).getage();
        }

    }
    public HumanProfile getHumanProfile()
    {
        return humanProfile;
    }

    public ArrayList<DogProfile> getDogProfiles() {return dogProfiles; }
}