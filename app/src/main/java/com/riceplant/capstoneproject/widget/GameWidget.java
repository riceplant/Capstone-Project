package com.riceplant.capstoneproject.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.riceplant.capstoneproject.R;
import com.riceplant.capstoneproject.activities.MainActivity;
import com.riceplant.capstoneproject.adapter.PagerAdapter;
import com.riceplant.capstoneproject.fragments.MyLibraryFragment;
import com.riceplant.capstoneproject.room.GameDao;
import com.riceplant.capstoneproject.room.GameRoomDatabase;
import com.riceplant.capstoneproject.room.MyGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class GameWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.game_widget);
        remoteViews.setOnClickPendingIntent(R.id.favoriteText, pendingIntent);

        setRemoteAdapter(context, remoteViews);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Toast.makeText(context,"onEnabled called", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Toast.makeText(context,"onDisabled called", Toast.LENGTH_LONG).show();
    }

    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.favouritesList,
                new Intent(context, WidgetService.class));
    }
}

