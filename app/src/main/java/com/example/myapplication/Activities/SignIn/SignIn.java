package com.example.myapplication.Activities.SignIn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.Activities.SignUp.SignUp;
import com.example.myapplication.Constants;
import com.example.myapplication.R;
import com.example.myapplication.Utlis.Utils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignIn extends AppCompatActivity implements MvpSignIn.View {


    private String email, pass;
    private MvpSignIn.Presenter presenter;
    private SharedPreferences.Editor editor;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.email_ed)
    TextInputLayout emailEd;
    @BindView(R.id.pass_ed)
    TextInputLayout passEd;

    @BindView(R.id.parent)
    ScrollView parent;

    @OnClick(R.id.sign_in)
    void signIn() {

        info();

        if (!validateEmail() | !validatepass())
            return;

        else
            presenter.connect(email, pass);
    }

    @OnClick(R.id.sign_up)
    void signUp() {

        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    @OnClick(R.id.skip)
    void skip() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        ButterKnife.bind(this);
        Utils.hideSoftKeyboard( SignIn.this);
        presenter = new SignInPresenter(this);
    }


    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void fail(String s) {
        Snackbar.make(progressBar, s, Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void sucess(String id) {
        Intent intent = new Intent(this, MainActivity.class);
        //to clear the stack and put the next activity at top
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        editor = getSharedPreferences(Constants.SHARED_PRE, MODE_PRIVATE).edit();
        editor.putString(Constants.ID, id);
        editor.apply();

    }


    private Boolean validateEmail() {


        if (email.isEmpty()) {
            emailEd.setError(getString(R.string.required));
            return false;
        } else
            emailEd.setError(null);
        return true;

    }

    private Boolean validatepass() {


        if (pass.isEmpty()) {
            passEd.setError(getString(R.string.required));
            return false;
        } else
            passEd.setError(null);
        return true;

    }


    private void info() {
        email = emailEd.getEditText().getText().toString().trim();
        pass = passEd.getEditText().getText().toString().trim();

    }
}
