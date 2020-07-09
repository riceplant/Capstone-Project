package com.riceplant.capstoneproject.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class GameViewModel extends AndroidViewModel {

    private LiveData<List<MyGame>> games;

    public GameViewModel(@NonNull Application application) {
        super(application);
        GameRoomDatabase database = GameRoomDatabase.getInstance(this.getApplication());
        games = database.gameDao().loadAllGames();
    }

    public LiveData<List<MyGame>> getGames() {
        return games;
    }
}
