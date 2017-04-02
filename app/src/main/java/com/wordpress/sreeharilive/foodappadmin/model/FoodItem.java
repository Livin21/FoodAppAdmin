package com.wordpress.sreeharilive.foodappadmin.model;


public class FoodItem {

    private String itemName;
    private String itemPrice;
    private String itemId;

    public FoodItem(String itemName, String itemPrice, String itemId) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
