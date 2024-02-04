package com.social.quizme.model;

public class profileModel {
    public profileModel(String profileId, String profileImgname) {
        this.profileId = profileId;
        this.profileImgname = profileImgname;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getProfileImgname() {
        return profileImgname;
    }

    public void setProfileImgname(String profileImgname) {
        this.profileImgname = profileImgname;
    }

    private  String profileId,profileImgname,email,password,name;






    public profileModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




    public profileModel(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
