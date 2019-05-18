package com.example.myapplication.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.Adpater.FavouriteAdapter;
import com.example.myapplication.FavouriteDB.AppDatabase;
import com.example.myapplication.FavouriteDB.FavouriteEntity;
import com.example.myapplication.FavouriteDB.MainViewModel;
import com.example.myapplication.R;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Favourite extends AppCompatActivity {

    @BindView(R.id.fav_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.data2)
    TextView tv;
    private FavouriteAdapter adapter;
    private AppDatabase mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the RecyclerView
        adapter = new FavouriteAdapter(this);
        recyclerView.setAdapter(adapter);

        mDb = AppDatabase.getInstance(this);
        SetupViewModel();
    }

    private void SetupViewModel() {

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getTasks().observe(this, new Observer<List<FavouriteEntity>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteEntity> fav) {
                if (!fav.isEmpty())
                    adapter.setFav(fav);
                else
                    tv.setVisibility(View.VISIBLE);
            }
        });
    }
}
