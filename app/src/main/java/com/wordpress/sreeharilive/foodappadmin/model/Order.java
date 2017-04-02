package com.wordpress.sreeharilive.foodappadmin.model;


import java.util.ArrayList;

public class Order {

    private String address;
    private String locality;
    private String userID;
    private double total;
    private long timestamp;
    private String modeOfPayment;
    private String orderID;

    private ArrayList<FoodItem> items;

    public Order(String orderID, String address, String locality, String userID, double total, ArrayList<FoodItem> items, long timestamp, String modeOfPayment) {
        this.orderID = orderID;
        this.address = address;
        this.locality = locality;
        this.userID = userID;
        this.total = total;
        this.items = items;
        this.timestamp = timestamp;
        this.modeOfPayment = modeOfPayment;
    }

    public String getOrderID() {
        return orderID;
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

    public long getTimestamp() {
        return timestamp;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }
}
