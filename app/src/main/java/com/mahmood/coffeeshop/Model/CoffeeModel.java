package com.mahmood.coffeeshop.Model;

import java.util.ArrayList;

public class CoffeeModel {

    String title, description, extra;
    float rating, price;
    ArrayList<String> picUrl;
    String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public CoffeeModel(String title, String description, String extra, float rating, float price, ArrayList<String> picUrl) {
        this.title = title;
        this.description = description;
        this.extra = extra;
        this.rating = rating;
        this.price = price;
        this.picUrl = picUrl;
    }

    public CoffeeModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ArrayList<String> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(ArrayList<String> picUrl) {
        this.picUrl = picUrl;
    }
}
