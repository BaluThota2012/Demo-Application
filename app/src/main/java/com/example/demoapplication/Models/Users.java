package com.example.demoapplication.Models;

import android.net.Uri;

public class Users {

    String proficPic, firstname,lastname, dob,gender, country,state,hometown,phnNo, telpnNo,userId;


    public Users(String userId,String proficPic, String firstname, String lastname, String dob, String gender, String country, String state, String hometown, String phnNo, String telpnNo) {
       this.userId=userId;
        this.proficPic = proficPic;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.gender = gender;
        this.country = country;
        this.state = state;
        this.hometown = hometown;
        this.phnNo = phnNo;
        this.telpnNo = telpnNo;
    }





    public Users(){}

    public String getProficPic() {
        return proficPic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setProficPic(String proficPic) {
        this.proficPic = proficPic;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getPhnNo() {
        return phnNo;
    }

    public void setPhnNo(String phnNo) {
        this.phnNo = phnNo;
    }

    public String getTelpnNo() {
        return telpnNo;
    }

    public void setTelpnNo(String telpnNo) {
        this.telpnNo = telpnNo;
    }
}
