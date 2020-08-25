package com.riceplant.capstoneproject.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.lifecycle.LiveData;

import com.riceplant.capstoneproject.R;
import com.riceplant.capstoneproject.data.Game;
import com.riceplant.capstoneproject.room.GameDao;
import com.riceplant.capstoneproject.room.GameRoomDatabase;
import com.riceplant.capstoneproject.room.MyGame;

import java.util.ArrayList;
import java.util.List;

public class DataProvider implements RemoteViewsService.RemoteViewsFactory {

    private GameDao gameDao;
    private ArrayList<MyGame> mGamesLibrary;
    private ArrayList<Game> mGames;
    private Context mContext;

    public DataProvider(Context context, Intent intent) {
        mContext = context;

        GameRoomDatabase db = null;
        LiveData<List<MyGame>> database = db.gameDao().loadAllGames();
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mGames != null) {
            return mGames.size();
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                R.layout.game_widget_list);
        view.setTextViewText(R.id.game_name_widget, mGames.get(position).getName());
        view.setTextViewText(R.id.game_rating_widget, mGames.get(position).getRating().toString());
        view.setTextViewText(R.id.game_release_widget, mGames.get(position).getReleaseDates().toString());
        return view;
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

    private void initData() {
        mGames.clear();
        for (int i = 0; i < mGamesLibrary.size(); i++) {
            Game game = new Game(
                    mGamesLibrary.get(i).getName(),
                    mGamesLibrary.get(i).getRating(),
                    mGamesLibrary.get(i).getReleaseDate()
            );
            mGames.add(game);
        }
    }
}
