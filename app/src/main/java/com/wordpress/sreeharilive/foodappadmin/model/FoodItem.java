package com.wordpress.sreeharilive.foodappadmin.model;


import java.io.Serializable;

public class FoodItem implements Serializable {

    private String itemName;
    private String category;
    private String itemId;
    private int qty;

    public FoodItem(String itemName, String category, String itemId, int qty) {
        this.itemName = itemName;
        this.category = category;
        this.itemId = itemId;
        this.qty = qty;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQty() {
        return qty;
    }
}
