package com.example.houserentals;

import java.io.Serializable;
import java.util.ArrayList;

public class HouseProperties implements Serializable{

    private String city;
    private String postalAddress;
    private String surfaceArea;
    private String constructionYear;
    private String numOfBedrooms;
    private String rentalPrice;
    private String status;
    private String photo;
    private String availabilityDate;
    private String description;
    private String period;
    private String hasBalcony;
    private String hasGarden;

    public static ArrayList<HouseProperties> rentHouses = new ArrayList<>();

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getSurfaceArea() {
        return surfaceArea;
    }

    public void setSurfaceArea(String surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    public String getConstructionYear() {
        return constructionYear;
    }

    public void setConstructionYear(String constructionYear) {
        this.constructionYear = constructionYear;
    }

    public String getNumOfBedrooms() {
        return numOfBedrooms;
    }

    public void setNumOfBedrooms(String numOfBedrooms) {
        this.numOfBedrooms = numOfBedrooms;
    }

    public String getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(String rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getAvailabilityDate() {
        return availabilityDate;
    }

    public void setAvailabilityDate(String availabilityDate) {
        this.availabilityDate = availabilityDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getHasBalcony() {
        return hasBalcony;
    }

    public void setHasBalcony(String hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public String getHasGarden() {
        return hasGarden;
    }

    public void setHasGarden(String hasGarden) {
        this.hasGarden = hasGarden;
    }

    @Override
    public String toString() {
        return "House{" +
                "city='" + city + '\'' +
                ", postalAddress='" + postalAddress + '\'' +
                ", surfaceArea='" + surfaceArea + '\'' +
                ", constructionYear='" + constructionYear + '\'' +
                ", numOfBedrooms='" + numOfBedrooms + '\'' +
                ", rentalPrice='" + rentalPrice + '\'' +
                ", status='" + status + '\'' +
                ", availabilityDate='" + availabilityDate + '\'' +
                ", propertyDescription='" + description + '\'' +
                ", hasBalcony='" + hasBalcony + '\'' +
                ", hasGarden='" + hasGarden + '\'' +
                '}';
    }

}
