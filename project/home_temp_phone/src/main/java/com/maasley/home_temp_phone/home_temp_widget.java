package com.maasley.home_temp_phone;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.maasley.android_lib.Temperature;

import java.util.Random;

/**
 * Implementation of App Widget functionality.
 */
public class home_temp_widget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Toast toast = Toast.makeText(context,"Test",Toast.LENGTH_LONG);
        toast.show();
        Temperature t = new Temperature("http://192.168.1.5/api/EthzAaSJ2U85HX5iD3fiQaLhew9xGqyNh8h5RlFa/sensors/11");
        CharSequence widgetText = t.getTemp();
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.home_temp_widget);
        views.setTextViewText(R.id.appwidget_temp, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
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

    public static int getRandom() {
        Random rand = new Random();
        int n = rand.nextInt(20) +10;
        return n;
    }
}

