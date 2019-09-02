package com.example.myapplication.Activities.SignIn;

public interface MvpSignIn {

    interface View {

        void showLoading();

        void hideLoading();

        void fail(String s);

        void sucess(String id);


    }

    interface Presenter {

        void connect( String email, String password);
    }

    interface Model {

        void model( String email, String password);
    }

}
