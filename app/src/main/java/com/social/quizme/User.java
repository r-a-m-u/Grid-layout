package com.social.quizme;

public class User {
    private String name;

    private String email;
    private String pass;

    public User(String profile) {
        this.profile = profile;
    }


    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }

    private String profile;
    private String referCode;
    private  long coins=25;
    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }


    public User(long coins) {
        this.coins = coins;
    }




    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getReferCode() {
        return referCode;
    }

    public void setReferCode(String referCode) {
        this.referCode = referCode;
    }



    public User(String name, String email, String pass, String referCode) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.referCode = referCode;
    }
}
