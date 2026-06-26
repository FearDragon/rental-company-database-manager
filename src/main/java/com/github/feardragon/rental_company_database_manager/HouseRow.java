package com.github.feardragon.rental_company_database_manager;

public class HouseRow {
    private int houseID;
    private String streetAddress;
    private String city;
    private String county;
    private String state;
    private String zipCode;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public HouseRow (int houseID, String streetAddress, String city, String county, String state,
                     String zipCode, String firstName, String lastName, String email, String phoneNumber){
        this.houseID = houseID;
        this.streetAddress = streetAddress;
        this.city = city;
        this.county = county;
        this.state = state;
        this.zipCode = zipCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getHouseID() {
        return houseID;
    }
    public void setHouseID(int houseID) {
        this.houseID = houseID;
    }
    public String getStreetAddress() {
        return streetAddress;
    }
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCounty() {
        return county;
    }
    public void setCounty(String county) {
        this.county = county;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
