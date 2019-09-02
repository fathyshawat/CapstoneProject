package com.example.myapplication.Activities.SignIn;

import android.util.Log;

import com.example.myapplication.Activities.MvpListener;
import com.example.myapplication.Activities.SignUp.MvpSignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.UserProfileChangeRequest;

import androidx.annotation.NonNull;

public class SignInModel implements MvpSignIn.Model {

    MvpListener listener;
    FirebaseAuth auth;

    SignInModel(MvpListener listener) {
        this.listener = listener;
        auth = FirebaseAuth.getInstance();
    }


    @Override
    public void model(String email, String password) {

        listener.load();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            listener.success(auth.getCurrentUser().getUid());
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                listener.failed("You are aleardy register");
                            else listener.failed(task.getException().getMessage());
                        }
                    }
                });
    }
}
