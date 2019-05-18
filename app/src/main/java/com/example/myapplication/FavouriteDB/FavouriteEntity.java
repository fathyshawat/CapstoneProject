package com.example.myapplication.FavouriteDB;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class FavouriteEntity {


    @PrimaryKey(autoGenerate = true)
    private int id;
    private String keyId;
    private String urlImage;
    private String resturantName;
    private String meal;
    private String address;
    private String desc;
    @Ignore
    public FavouriteEntity(String keyId, String urlImage, String resturantName, String meal,
                           String address, String desc) {
        this.keyId = keyId;
        this.urlImage = urlImage;
        this.resturantName = resturantName;
        this.meal = meal;
        this.address = address;
        this.desc = desc;
    }

    public FavouriteEntity(int id, String keyId, String urlImage, String resturantName,
                           String meal, String address, String desc) {
        this.id = id;
        this.keyId = keyId;
        this.urlImage = urlImage;
        this.resturantName = resturantName;
        this.meal = meal;
        this.address = address;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getResturantName() {
        return resturantName;
    }

    public void setResturantName(String resturantName) {
        this.resturantName = resturantName;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;

    }
}
