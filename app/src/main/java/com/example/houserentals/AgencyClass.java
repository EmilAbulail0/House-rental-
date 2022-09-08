package com.example.houserentals;

import java.io.Serializable;

public class AgencyClass implements Serializable {
    private  String Email;
    private  String Name;
    private  String Password;
    private  String Country;
    private  String City;
    private  String Phone;


 public AgencyClass(String country, String name, String email, String password, String city, String phone) {
     Email = email;
     Name = name;
     Password = password;
     Country = country;
     City = city;
     Phone = phone;
    }

    public AgencyClass() {

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
