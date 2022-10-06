package com.example.assignmentdatabase.models;

public class CustomerCountry {

    private String country;
    private int numberOfCustomers;

    public CustomerCountry(String country, int numberOfCustomers) {
        this.country = country;
        this.numberOfCustomers = numberOfCustomers;
    }

    public String getCountry() {
        return country;
    }

    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }
}
