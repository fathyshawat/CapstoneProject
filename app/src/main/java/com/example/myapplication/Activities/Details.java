package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.FavouriteDB.AppDatabase;
import com.example.myapplication.FavouriteDB.FavouriteEntity;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Details extends AppCompatActivity {


    private String url, resturant, meal, address, description, keyid;
    private AppDatabase mDb;
    private LiveData<List<FavouriteEntity>> mMyFavData;

    private Boolean like = true;
    private int DbId;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.restu)
    TextView resturantNameEd;
    @BindView(R.id.meal)
    TextView mealEd;
    @BindView(R.id.desc)
    TextView descEd;
    @BindView(R.id.address)
    TextView addressEd;
    @BindView(R.id.like)
    ImageView imgLike;

    @OnClick(R.id.like)
    void like() {
        if (like) {
            FavouriteEntity favouriteEntity = new FavouriteEntity(keyid, url, resturant, meal, address, description);
            mDb.taskDao().insertFav(favouriteEntity);
            like = false;
            imgLike.setImageDrawable(getResources().getDrawable(R.drawable.like));
        } else {
            FavouriteEntity favouriteEntity = new FavouriteEntity(DbId, keyid, url, resturant, meal, address, description);
            mDb.taskDao().deleteFav(favouriteEntity);
            like = true;
            imgLike.setImageDrawable(getResources().getDrawable(R.drawable.unlike));

        }
    }

    @OnClick(R.id.fav_list)
    void favList() {
        Intent intent = new Intent(getApplicationContext(), Favourite.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        ButterKnife.bind(this);
        mDb = AppDatabase.getInstance(getApplicationContext());
        checkFavourite();
        url = getIntent().getStringExtra("img");
        resturant = getIntent().getStringExtra("restu");
        address = getIntent().getStringExtra("address");
        meal = getIntent().getStringExtra("meal");
        description = getIntent().getStringExtra("desc");
        keyid = getIntent().getStringExtra("id");
        addressEd.setText(address);
        mealEd.setText(meal);
        resturantNameEd.setText(resturant);
        descEd.setText(description);
        Picasso.get().load(url).fit().into(img);
        Picasso.get().setIndicatorsEnabled(true);

    }

    private void checkFavourite() {

        mMyFavData = mDb.taskDao().loadAllFav();
        mMyFavData.observe(this, new Observer<List<FavouriteEntity>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteEntity> favouriteDBS) {
                for (int i = 0; i < favouriteDBS.size(); i++) {
                    if (address.equals(favouriteDBS.get(i).getAddress())) {
                        like = false;
                        DbId = favouriteDBS.get(i).getId();
                        break;
                    }
                }
            }
        });

    }
}
