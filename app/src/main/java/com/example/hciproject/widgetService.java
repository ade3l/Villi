package com.example.hciproject;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class widgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new widgetFactory(getApplicationContext(), intent);
    }
    class widgetFactory implements RemoteViewsFactory{
        private Context context;
        private int appWidgetId;
        private String[] classes={"CAO","DAA","DBMS","HCI"};
        widgetFactory(Context context,Intent intent){
            this.context=context;
            this.appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return classes.length;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews view=new RemoteViews(context.getPackageName(),R.layout.widget_item);
            view.setTextViewText(R.id.subjectName,classes[i]);
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
