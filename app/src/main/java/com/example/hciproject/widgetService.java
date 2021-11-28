package com.example.hciproject;

import static com.example.hciproject.widgetProvider.MODE_ASSIGNMENTS;
import static com.example.hciproject.widgetProvider.MODE_TIMETABLE;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.hciproject.data.DataSource;
import com.example.hciproject.objects.Assignment;
import com.example.hciproject.objects.Classes;

import java.util.ArrayList;
import java.util.List;

public class widgetService extends RemoteViewsService {
    List<Classes> listOfclasses=new ArrayList<>();
    List<Assignment> listOfAssignments=new ArrayList<>();
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new widgetFactory(getApplicationContext(), intent);
    }
    class widgetFactory implements RemoteViewsFactory{
        final private Context context;
        private final int appWidgetId;
        int day;
        String mode;
        SharedPreferences prefs;

        widgetFactory(Context context,Intent intent){
            this.context=context;
            this.appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
            prefs = context.getSharedPreferences("com.example.hciproject",Context.MODE_PRIVATE);
            day=prefs.getInt("day", 0);
            mode=prefs.getString("mode", MODE_TIMETABLE);
        }
        @Override
        public void onCreate() {
            day=prefs.getInt("day", 0);
            mode=prefs.getString("mode", MODE_TIMETABLE);
        }

        @Override
        public void onDataSetChanged() {
            //TODO: On data change
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            day=prefs.getInt("day", 0);
            mode=prefs.getString("mode", MODE_TIMETABLE);
            int size = 0;
            if(mode.equals(MODE_TIMETABLE)){
                try {
                    listOfclasses = DataSource.getClasses(DataSource.getDays().get(day));
                }
                catch (NullPointerException e){
                    DataSource.initClasses(context);
                    listOfclasses = DataSource.getClasses(DataSource.getDays().get(day));

                }
                size=listOfclasses.size();
            }
            else if (mode.equals(MODE_ASSIGNMENTS)){
                try {
                    listOfAssignments=DataSource.getPendingAssignments();
                }
                catch (NullPointerException e){
                    DataSource.initAssignments(context);
                    listOfAssignments=DataSource.getPendingAssignments();
                }
                size=listOfAssignments.size();
            }
            return size;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews view;
            if(mode.equals(MODE_TIMETABLE)){
                view=new RemoteViews(context.getPackageName(),R.layout.widget_class_item);
                day=prefs.getInt("day", 0);
                Classes myClass=listOfclasses.get(i);
                view.setTextViewText(R.id.subjectName, myClass.getSubject());
                view.setTextViewText(R.id.time,String.format("%s - %s",myClass.getStartTime(),myClass.getEndTime()));
            }
            else{
                view=new RemoteViews(context.getPackageName(),R.layout.widget_assignment_item);
                Assignment assignment= listOfAssignments.get(i);
                if(!assignment.getSubject().equals("")){
                    view.setViewVisibility(R.id.subject_name, View.VISIBLE);
                    view.setTextViewText(R.id.subject_name,assignment.getSubject());
                    //Log the subject name
                    Log.d("mine subject",assignment.getSubject());
                }
                view.setTextViewText(R.id.assignment_name,assignment.getName());
                view.setTextViewText(R.id.due_date,DataSource.getDateFromMillis(Long.parseLong(assignment.getDueDate()))+"  ");
                view.setTextViewText(R.id.due_time,assignment.getDueTime());
            }

            return view;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
