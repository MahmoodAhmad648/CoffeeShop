package com.mahmood.coffeeshop.Model;

public class CartModel {
    private String coffeeName;
    private String size;
    private int quantity;
    private float price;
    private String imageUrl;

    public CartModel(String coffeeName, String size, int quantity, float price, String imageUrl) {
        this.coffeeName = coffeeName;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public CartModel() {
    }
    // Getters and setters for the fields

    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
