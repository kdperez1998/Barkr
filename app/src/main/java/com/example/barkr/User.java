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
        dogProfiles.add(new DogProfile("Bagel", "Corgi", "m", false, true, "hello", 6));
    }
    public HumanProfile getHumanProfile()
    {
        return humanProfile;
    }

    public ArrayList<DogProfile> getDogProfiles() {return dogProfiles; }
}