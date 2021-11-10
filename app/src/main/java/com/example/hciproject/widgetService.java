package com.example.hciproject;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.hciproject.data.DataSource;
import com.example.hciproject.objects.Classes;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class widgetService extends RemoteViewsService {
    List<Classes> listOfclasses=new ArrayList<>();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new widgetFactory(getApplicationContext(), intent);
    }
    class widgetFactory implements RemoteViewsFactory{
        final private Context context;
        private final int appWidgetId;
        int day;
        SharedPreferences prefs;

        widgetFactory(Context context,Intent intent){
            this.context=context;
            this.appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
            prefs = context.getSharedPreferences("com.example.hciproject",Context.MODE_PRIVATE);
            day=prefs.getInt("day", 0);
        }
        @Override
        public void onCreate() {
            Log.i("mine","Widget Factory on create");
            day=prefs.getInt("day", 0);
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            day=prefs.getInt("day", 0);
            listOfclasses= DataSource.getClasses(DataSource.getDays().get(day));
            return listOfclasses.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews view=new RemoteViews(context.getPackageName(),R.layout.widget_item);
            day=prefs.getInt("day", 0);
            Classes myClass=listOfclasses.get(i);
            view.setTextViewText(R.id.subjectName, myClass.getSubject());
            view.setTextViewText(R.id.time,String.format("%s - %s",myClass.getStartTime(),myClass.getEndTime()));
            return view;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
