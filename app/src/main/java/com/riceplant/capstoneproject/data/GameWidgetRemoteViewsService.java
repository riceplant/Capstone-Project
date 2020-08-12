package com.riceplant.capstoneproject.data;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.lifecycle.LiveData;

import com.riceplant.capstoneproject.room.GameDao;
import com.riceplant.capstoneproject.room.GameRoomDatabase;
import com.riceplant.capstoneproject.room.MyGame;

import java.util.ArrayList;
import java.util.List;

public class GameWidgetRemoteViewsService extends RemoteViewsService {

    private static ArrayList<MyGame> mGamesLibrary = new ArrayList<>();
    private static LiveData<List<MyGame>> mGame;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GameWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class GameWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private GameDao gameDao;
        private LiveData<List<MyGame>> mGames;

        GameWidgetRemoteViewsFactory(Context context, Intent intent) {
            GameRoomDatabase db = GameRoomDatabase.getInstance(context);
            gameDao = db.gameDao();
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            mGames = gameDao.loadAllGames();
            mGame = mGames;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            return null;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }

}