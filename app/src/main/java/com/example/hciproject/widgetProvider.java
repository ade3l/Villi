package com.example.hciproject;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

public class widgetProvider extends AppWidgetProvider {
    private static final String GO_LEFT_TAG = "Left click";
    private static final String GO_RIGHT_TAG = "Right click";
    private static int day;
    SharedPreferences prefs;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetId:appWidgetIds){
            prefs = context.getSharedPreferences("com.example.hciproject",Context.MODE_PRIVATE);
            day=prefs.getInt("day", 0);
            Intent serviceIntent=new Intent(context,widgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.widget_layout);
            Log.i("mine","before 29");
            views.setOnClickPendingIntent(R.id.left,getPendingSelfIntent(context, GO_LEFT_TAG));
            views.setOnClickPendingIntent(R.id.right,getPendingSelfIntent(context, GO_RIGHT_TAG));
            Log.i("mine","after 29");
            views.setRemoteAdapter(R.id.listView,serviceIntent);
            views.setEmptyView(R.id.listView,R.id.emptyTextView);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        prefs = context.getSharedPreferences("com.example.hciproject",Context.MODE_PRIVATE);
        day=prefs.getInt("day", 0);
//        TODO: Functions for the arrow buttons
        if(intent.getAction().equals(GO_LEFT_TAG)){
            Toast.makeText(context, "Go back a day", Toast.LENGTH_SHORT).show();
            RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.widget_layout);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds= appWidgetManager.getAppWidgetIds(new ComponentName(context, widgetProvider.class));
            day--;
            day%=7;
            prefs.edit().putInt("day", day).commit();
            Log.i("mine", String.valueOf(day));
            views.setTextViewText(R.id.day, String.valueOf(day));
            appWidgetManager.updateAppWidget(appWidgetIds, views);

        }
        else if( intent.getAction().equals(GO_RIGHT_TAG)){
            Toast.makeText(context, "Go forward a day", Toast.LENGTH_SHORT).show();
        }


    }
}
