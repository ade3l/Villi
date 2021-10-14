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
    String daysList[] = {"Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday","Sunday"};
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetId:appWidgetIds){
            prefs = context.getSharedPreferences("com.example.hciproject",Context.MODE_PRIVATE);
            day=prefs.getInt("day", day);
            Intent serviceIntent=new Intent(context,widgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
            serviceIntent.putExtra("Day",0);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.widget_layout);
//            Log.i("mine","before 29");
            views.setOnClickPendingIntent(R.id.left,getPendingSelfIntent(context, GO_LEFT_TAG));
            views.setOnClickPendingIntent(R.id.right,getPendingSelfIntent(context, GO_RIGHT_TAG));
//            Log.i("mine","after 29");
            views.setRemoteAdapter(R.id.listView,serviceIntent);
            views.setEmptyView(R.id.listView,R.id.emptyTextView);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        prefs = context.getSharedPreferences("com.example.hciproject",Context.MODE_PRIVATE);
        day=prefs.getInt("day", 0);
//        TODO: Functions for the arrow buttons
        if(intent.getAction().equals(GO_LEFT_TAG)){
            if(day==0) day=6;
            else day--;
            prefs.edit().putInt("day", day).commit();
            updateTT(context, intent);
        }
        else if( intent.getAction().equals(GO_RIGHT_TAG)){
            if(day==6) day=0;
            else day++;
            prefs.edit().putInt("day", day).commit();

            updateTT(context,intent);
//            RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.widget_layout);
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//            int[] appWidgetIds= appWidgetManager.getAppWidgetIds(new ComponentName(context, widgetProvider.class));
//            
//            prefs.edit().putInt("day", day).commit();
//            Log.i("mine", String.valueOf(day));
//            views.setTextViewText(R.id.day, daysList[day]);
//            appWidgetManager.updateAppWidget(appWidgetIds, views);
        }


    }
    void updateTT(Context context, Intent intent){
        RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.widget_layout);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds= appWidgetManager.getAppWidgetIds(new ComponentName(context, widgetProvider.class));
        Intent serviceIntent=new Intent(context,widgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetIds[0]);
        serviceIntent.putExtra("Day",day);
        Log.i("mine","sending"+String.valueOf(day));
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.listView,serviceIntent);
        views.setTextViewText(R.id.day, daysList[day]);
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }
    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}
