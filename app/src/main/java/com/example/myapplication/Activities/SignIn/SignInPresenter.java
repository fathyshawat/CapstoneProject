package com.example.myapplication.Activities.SignIn;

import com.example.myapplication.Activities.MvpListener;
import com.example.myapplication.Activities.SignUp.MvpSignUp;

public class SignInPresenter implements MvpSignIn.Presenter, MvpListener {

    MvpSignIn.View view;
    MvpSignIn.Model model;

    SignInPresenter(MvpSignIn.View view) {
        this.view = view;
        model = new SignInModel(this);
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
    public void connect(String email, String password) {
        model.model(email,password);
    }
}
