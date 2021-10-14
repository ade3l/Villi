package com.example.hciproject;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class widgetService extends RemoteViewsService {
    Day mon=new Day("Monday"),
        tue=new Day("Tueday"),
        wed=new Day("Wednesday"),
        thu=new Day("Thursday"),
        fri=new Day("Friday"),
        sat=new Day("Saturday"),
        sun=new Day("Sunday");


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new widgetFactory(getApplicationContext(), intent);
    }
    class widgetFactory implements RemoteViewsFactory{
        private Context context;
        private int appWidgetId;
        int day;
        private String[] classes={"CAO","DAA","DBMS","HCI","CAO","DAA","DBMS","HCI"};
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

            mon.addToSchedule(LocalTime.of(8,0,0),"CAO");
            mon.addToSchedule(LocalTime.of(9,0,0),"DAA");
            mon.addToSchedule(LocalTime.of(12,0,0),"HCI");
            mon.addToSchedule(LocalTime.of(11,0,0),"DBMS");

            tue.addToSchedule(LocalTime.of(9,0,0),"DBMS");
            tue.addToSchedule(LocalTime.of(10,0,0),"HCI");
            tue.addToSchedule(LocalTime.of(11,0,0),"STS");
            tue.addToSchedule(LocalTime.of(14,0,0),"OS");

            wed.addToSchedule(LocalTime.of(9,0,0),"DBMS");
            wed.addToSchedule(LocalTime.of(9,0,0),"CAO");
            wed.addToSchedule(LocalTime.of(9,0,0),"DAA");


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
            switch(day){
                case 0:
                    return mon.getSchedule().size();
                case 1:
                    return tue.getSchedule().size();
                case 2:
                    return wed.getSchedule().size();

                default: return 0;
            }
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews view=new RemoteViews(context.getPackageName(),R.layout.widget_item);
            day=prefs.getInt("day", 0);
            LocalTime x;
            SortedMap<LocalTime,String> schedule = new TreeMap<LocalTime,String>();
            switch(day){
                case 0:
                    schedule=mon.getSchedule();
                    break;
                case 1:
                    schedule=tue.getSchedule();
                    break;
                case 2:
                    schedule=wed.getSchedule();
                    break;
                default: view.setTextViewText(R.id.subjectName,classes[i]);;
            }
            x= (LocalTime) schedule.keySet().toArray()[i];
            view.setTextViewText(R.id.subjectName, schedule.get(x));

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
