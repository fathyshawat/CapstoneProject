package com.example.myapplication.FavouriteDB;

import com.example.myapplication.Activities.Favourite;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FavouriteDao {


    @Query("SELECT * FROM FavouriteEntity ")
    LiveData<List<FavouriteEntity>> loadAllFav();

    @Insert
    void insertFav(FavouriteEntity taskEntry);

    @Query("DELETE FROM FavouriteEntity WHERE id = :id")
    void deleteFav(int id);

    @Delete
    void deleteFav(FavouriteEntity favourite);

}
