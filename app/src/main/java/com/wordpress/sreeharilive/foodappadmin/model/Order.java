package com.wordpress.sreeharilive.foodappadmin.model;


import java.util.ArrayList;

public class Order {

    private String address;
    private String locality;
    private String userID;
    private double total;

    private ArrayList<FoodItem> items;

    public Order(String address, String locality, String userID, double total, ArrayList<FoodItem> items) {
        this.address = address;
        this.locality = locality;
        this.userID = userID;
        this.total = total;
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public String getLocality() {
        return locality;
    }

    public String getUserID() {
        return userID;
    }

    public double getTotal() {
        return total;
    }

    public ArrayList<FoodItem> getItems() {
        return items;
    }
}
