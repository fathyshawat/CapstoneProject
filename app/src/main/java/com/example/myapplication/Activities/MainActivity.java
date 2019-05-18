package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.Adpater.MealsAdapter;
import com.example.myapplication.R;
import com.example.myapplication.model.MealModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @OnClick(R.id.fab)
    public void addMeal(View view) {
        Intent intent = new Intent(this, AddMeal.class);
        startActivity(intent);
    }

    @BindView(R.id.main_recyler)
    RecyclerView mealsRecycler;
    @BindView(R.id.adView)
    AdView mAdView;
    @BindView(R.id.progress_main)
    ProgressBar progressBar;
    @BindView(R.id.data)
    TextView dataText;

    @OnClick(R.id.fav)
    void favList() {
        Intent intent = new Intent(this, Favourite.class);
        startActivity(intent);
    }

    private MealsAdapter adapter;
    private List<MealModel> data;
    private DatabaseReference mDatabaseReference;
    private ValueEventListener valueEventListener;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        admob();
        data = new ArrayList<>();
        mealsRecycler.setLayoutManager(new LinearLayoutManager(this));
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("meals");
        valueEventListener = mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data.clear();
                for (DataSnapshot post : dataSnapshot.getChildren()) {
                    MealModel mealModel = post.getValue(MealModel.class);
                    data.add(mealModel);
                }
                if (data.size() == 0) {
                    progressBar.setVisibility(View.GONE);
                    dataText.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    dataText.setVisibility(View.INVISIBLE);
                    adapter = new MealsAdapter(MainActivity.this, data);
                    mealsRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Snackbar.make(progressBar, databaseError.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });


    }

    private void admob() {
        MobileAds.initialize(this, getString(R.string.admobid));
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getString(R.string.unitid));
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }


}
