package com.example.myapplication.model;

public class MealModel {

    private String meal;
    private String image;
    private String restaurant;
    private String description;
    private String address;


    public MealModel(){}
    public MealModel(String meal, String image, String restaurant, String description, String address) {
        this.meal = meal;
        this.image = image;
        this.restaurant = restaurant;
        this.description = description;
        this.address = address;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
