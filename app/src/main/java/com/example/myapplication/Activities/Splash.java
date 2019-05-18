package com.example.myapplication.Activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import gr.net.maroulis.library.EasySplashScreen;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasySplashScreen config = new EasySplashScreen(this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(2000)
                .withBackgroundResource(android.R.color.holo_green_light)
                .withFooterText(getString(R.string.copyright))
                .withHeaderText(getString(R.string.app_name))
                .withLogo(R.drawable.fried);

        Typeface pacificoFont = Typeface.createFromAsset(getAssets(), "cairo_bold.ttf");
        config.getHeaderTextView().setTypeface(pacificoFont);
        config.getHeaderTextView().setTextColor(Color.WHITE);
        //finally create the view
        View easySplashScreenView = config.create();
        setContentView(easySplashScreenView);
    }
}
