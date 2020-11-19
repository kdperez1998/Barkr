package com.example.barkr;

public class User {
    private String userName, Password;
    private HumanProfile humanProfile;

    public static void main(String[] args) {

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

    public HumanProfile getHumanProfile()
    {
        return humanProfile;
    }
}