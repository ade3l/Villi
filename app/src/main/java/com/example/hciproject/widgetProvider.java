package com.example.hciproject;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class widgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetId:appWidgetIds){
            Intent serviceIntent=new Intent(context,widgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.widget_layout);
            views.setRemoteAdapter(R.id.listView,serviceIntent);
            views.setEmptyView(R.id.listView,R.id.emptyTextView);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }
}
