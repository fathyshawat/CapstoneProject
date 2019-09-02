package com.example.myapplication.Activities.SignUp;

public interface MvpSignUp {

    interface View {

        void showLoading();

        void hideLoading();

        void fail(String s);

        void sucess(String id);

        void checkNetwork(String message);

    }

    interface Presenter {

        void connect(String name,String email, String password);
    }

    interface Model {

        void model(String name , String email, String password);
    }

}
