package com.riceplant.capstoneproject.data;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.lifecycle.LiveData;

import com.riceplant.capstoneproject.R;
import com.riceplant.capstoneproject.room.GameDao;
import com.riceplant.capstoneproject.room.GameRoomDatabase;
import com.riceplant.capstoneproject.room.MyGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class GameWidget extends AppWidgetProvider {

    private static ArrayList<MyGame> mGamesLibrary = new ArrayList<>();
    private static LiveData<List<MyGame>> mGame;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.game_widget);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(R.id.app_name, appWidgetId);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public class GameWidgetRemoteService extends RemoteViewsService {

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
}

