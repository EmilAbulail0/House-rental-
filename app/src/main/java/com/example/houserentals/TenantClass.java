package com.example.houserentals;

import java.io.Serializable;

public class TenantClass implements Serializable {
    private  String Email;
    private  String Firstname;
    private  String Lastname;
    private  String Gender;
    private  String Password;
    private  String Nationality;
    private int Salary;
    private  String Occupation;
    private  int Familysize;
    private  String country;
    private  String City;
    private  String Phone;




    public TenantClass(String country, String occupation, String email, String first_name, String password, String gender, String nationality, String city, String phone_number, int gross_monthly_salary, int family_size, String lastname) {
        Email = email;
        Firstname = first_name;
        Lastname = lastname;
        Gender = gender;
        Password = password;
        Nationality = nationality;
        Salary = gross_monthly_salary;
        Occupation = occupation;
        Familysize = family_size;
        this.country = country;
        City = city;
        Phone = phone_number;
    }

    public TenantClass() {

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public double getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }

    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    public int getFamily_Size() {
        return Familysize;
    }

    public void setFamily_Size(int family_Size) {
        Familysize = family_Size;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
