package com.example.myapplication.Activities.SignUp;

import com.example.myapplication.Activities.MvpListener;

public class SignUpPresenter implements MvpSignUp.Presenter, MvpListener {

    MvpSignUp.View view;
    MvpSignUp.Model model;

    SignUpPresenter(MvpSignUp.View view) {
        this.view = view;
        model = new SignUpModel(this);
    }

    @Override
    public void success(String id) {
        view.hideLoading();
        view.sucess(id);
    }

    @Override
    public void failed(String s) {
        view.fail(s);
        view.hideLoading();

    }

    @Override
    public void load() {
        view.showLoading();
    }

    @Override
    public void connect(String name,String email, String password) {
        model.model(name,email, password);
    }
}
