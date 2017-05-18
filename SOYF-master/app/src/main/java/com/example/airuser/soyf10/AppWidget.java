package com.example.airuser.soyf10;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.format.DateFormat;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import java.util.logging.LogRecord;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {
    SharedPreferences settings;
    int total;
    int daily;
    Handler handler;

    @Override
    public void onUpdate(Context context, final AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        final int N = appWidgetIds.length;
        settings = context.getSharedPreferences("Pref_data", 0);
        total = settings.getInt("totalSteps", 0);
        daily = settings.getInt("dailySteps", 0);

        for (int i = 0; i < N; i++) {
            final int appWidgetId = appWidgetIds[i];

            Intent clockIntent = new Intent(context, AppWidget.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clockIntent, 0);

            final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
            //views.setOnClickPendingIntent(R.id.update, pendingIntent);

            final Handler mHandler = new Handler();
            Runnable continuousRunnable = new Runnable(){
                public void run() {

                    total = settings.getInt("totalSteps", 0);
                    daily = settings.getInt("dailySteps", 0);
                    views.setTextViewText(R.id.total, "total: "+total);
                    views.setTextViewText(R.id.daily, "daily: " + daily);
                    appWidgetManager.updateAppWidget(appWidgetId, views);
                    mHandler.postDelayed(this, 10000);
                }
            };// Update text every second
            continuousRunnable.run();
            appWidgetManager.updateAppWidget(appWidgetId, views);
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
}

