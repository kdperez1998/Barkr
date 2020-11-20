package com.example.barkr;

public class DogProfile {
    private String name, breed, gender, bio;
    private boolean spayedNeutered, shotUpToDate;
    private int age;

    public DogProfile (String setName, String setBreed, String setGender, boolean setSpayedNeutered,
                       boolean setShotUpToDate, String setBio, int setAge) {
        name = setName;
        breed = setBreed;
        gender = setGender;
        spayedNeutered = setSpayedNeutered;
        shotUpToDate = setShotUpToDate;
        bio = setBio;
        age = setAge;

    }
    public void setname(String name) {
        this.name = name;
    }

    public void setbreed(String breed) {
        this.breed = breed;
    }
    public void setgender(String gender) {
        this.gender = gender;
    }
    public void setspayedNeutered(boolean spayedNeutered) {
        this.spayedNeutered = spayedNeutered;
    }
    public void setshotDate(boolean setShotUpToDate) {
        this.shotUpToDate = setShotUpToDate;
    }
    public void setbio(String bio) {
        this.bio = bio;
    }
    public void setage(int age) {
        this.age = age;
    }
    public String getname() {
        return this.name;
    }

    public String getbreed() {
        return this.breed;
    }
    public String getgender() {
        return this.gender;
    }
    public boolean getspayedNeutered() {
        return this.spayedNeutered;
    }
    public boolean getShotUpToDate() {
        return this.shotUpToDate;
    }
    public String getbio() {
        return this.bio;
    }
    public int getage() {
        return this.age;
    }

    public int getDogProfileAge () {
        return age;
    }
}