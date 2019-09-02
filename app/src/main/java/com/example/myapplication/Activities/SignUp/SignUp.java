package com.example.myapplication.Activities.SignUp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.example.myapplication.Activities.MainActivity;
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

public class SignUp extends AppCompatActivity implements MvpSignUp.View {


    private String pass = "", confPass = "";
    private String name, email;
    private MvpSignUp.Presenter presenter;
    private SharedPreferences.Editor editor;

    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.fname_ed)
    TextInputLayout fNameEd;
    @BindView(R.id.email_ed)
    TextInputLayout emailEd;
    @BindView(R.id.pass_ed)
    TextInputLayout passEd;
    @BindView(R.id.conf_pass)
    TextInputLayout confPassEd;
    @BindView(R.id.parent1)
    ScrollView parent;

    @OnClick(R.id.sign_up)
    void signUp() {

        info();

        if (!validateName() | !validateEmail() | !validatepass() | !validateConfirmPass())
            return;
        else if (!pass.equals(confPass))
            Snackbar.make(progressBar, getString(R.string.re_pass), Snackbar.LENGTH_LONG).show();
        else
            presenter.connect(name, email, pass);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ButterKnife.bind(this);
        Utils.setupUI(parent, SignUp.this);
        presenter = new SignUpPresenter(this);
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

    @Override
    public void checkNetwork(String message) {
        Snackbar.make(progressBar, message, Snackbar.LENGTH_LONG).show();
    }

    private Boolean validateName() {

        if (name.isEmpty()) {
            fNameEd.setError(getString(R.string.required));
            return false;
        } else
            fNameEd.setError(null);
        return true;

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

    private Boolean validateConfirmPass() {


        if (confPass.isEmpty()) {
            confPassEd.setError(getString(R.string.required));
            return false;
        } else
            confPassEd.setError(null);
        return true;

    }

    private void info() {
        name = fNameEd.getEditText().getText().toString().trim();
        email = emailEd.getEditText().getText().toString().trim();
        pass = passEd.getEditText().getText().toString().trim();
        confPass = confPassEd.getEditText().getText().toString().trim();

    }
}
