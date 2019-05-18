package com.example.myapplication.FavouriteDB;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<FavouriteEntity>> fav;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(getApplication());
        fav = appDatabase.taskDao().loadAllFav();
    }

    public LiveData<List<FavouriteEntity>> getTasks() {
        return fav;
    }
}
