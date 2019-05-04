package com.example.myapplication.Activities;

import android.os.Bundle;
import android.widget.Toolbar;

import com.example.myapplication.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import butterknife.BindView;

public class Details extends AppCompatActivity {

//    @BindView(R.id.app_bar)
//    AppBarLayout appBarLayout;
//    @BindView(R.id.nested_scroll_view)
//    NestedScrollView nestedScrollView;
//    @BindView(R.id.toolbar_layout)
//    CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);


    }
}
