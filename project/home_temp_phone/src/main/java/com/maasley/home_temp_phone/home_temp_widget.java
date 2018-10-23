package com.maasley.home_temp_phone;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.maasley.android_lib.Temperature;

/**
 * Implementation of App Widget functionality.
 */
public class home_temp_widget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

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
        Temperature t = new Temperature("http://192.168.1.5/api/EthzAaSJ2U85HX5iD3fiQaLhew9xGqyNh8h5RlFa/sensors/11");
        CharSequence widgetText = t.getTemp();
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.home_temp_widget);
        views.setTextViewText(R.id.appwidget_temp, widgetText);
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

