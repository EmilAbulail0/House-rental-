package com.example.houserentals;

import java.util.ArrayList;

public class UserClass {
    public static ArrayList<UserClass> allUsers = new ArrayList<>();

    boolean agencyFlag;
    String email;
    String password;


    //
    public UserClass(String email, String password, boolean agencyFlag) {
        this.agencyFlag = agencyFlag;
        this.email = email;
        this.password = password;

    }

    public UserClass(){ }

    public boolean isAgencyFlag() {
        return agencyFlag;
    }

    public void setAgencyFlag(boolean admin) {
        agencyFlag = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "isAdmin=" + agencyFlag +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
