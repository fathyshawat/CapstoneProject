package com.example.myapplication.Activities.SignUp;

import android.util.Log;

import com.example.myapplication.Activities.MvpListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import androidx.annotation.NonNull;

public class SignUpModel implements MvpSignUp.Model {

    MvpListener listener;
    FirebaseAuth auth;

    SignUpModel(MvpListener listener) {
        this.listener = listener;
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void model(String name, String email, String password) {


        listener.load();
       UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
    /*   FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updateProfile(profileUpdates);*/
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                        auth.getCurrentUser().updateProfile(profileUpdates);
                            listener.success(auth.getCurrentUser().getUid());
                        } else {
                            listener.failed(task.getException().getMessage());
                            Log.d("Firebasse", task.getException().getMessage());
                        }
                    }
                });
    }
}
