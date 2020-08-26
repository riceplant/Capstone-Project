package com.riceplant.capstoneproject.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface GameDao {

    @Query("SELECT * FROM game ORDER BY mId")
    LiveData<List<MyGame>> loadAllGames();

    // for widget only
    @Query("SELECT * FROM game ORDER BY mId")
    List<MyGame> loadAllGamesSync();

    @Insert
    void insertGame(MyGame myGame);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateGame(MyGame myGame);

    @Delete
    void deleteGame(MyGame myGame);

    @Query("SELECT * FROM game WHERE mId = :id")
    MyGame loadGameById(int id);
}
