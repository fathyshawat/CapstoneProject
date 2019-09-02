package com.example.myapplication.Activities;

public interface MvpListener {

    void success(String id);
    void failed(String s);
    void load();
}
