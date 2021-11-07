package com.example.hciproject.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSource {
    static List<String> subs=new ArrayList<>();
    private static SQLiteDatabase subjectsDb, classesDb;
    public static void initSubjects(Context context){
        subs.clear();
//        TODO: Populate the subs array with data from sqldb
        subjectsDb = context.openOrCreateDatabase("subjects",Context.MODE_PRIVATE,null);
        subjectsDb.execSQL("CREATE TABLE IF NOT EXISTS subjects(subjectName VARCHAR)");
        Cursor c=subjectsDb.rawQuery("SELECT * FROM subjects",null);
        int subjectsNameIndex=c.getColumnIndex("subjectName");
        c.moveToFirst();
        try {
            while (c != null) {
                subs.add(c.getString(subjectsNameIndex));
                c.moveToNext();
            }
        }
        catch(Exception e){
            Log.i("mine","SQL error");
            e.printStackTrace();
        }
    }

    public static void addSubject(String subjectName){
        subjectsDb.execSQL("INSERT INTO subjects (subjectName) VALUES ('"+subjectName+"')");
        subs.add(subjectName);
    }

    public static void initClasses(Context context){
        classesDb= context.openOrCreateDatabase("classes",Context.MODE_PRIVATE,null);
        classesDb.execSQL("CREATE TABLE IF NOT EXISTS classes(day VARCHAR, subjectName VARCHAR, startTime INTEGER, endTime INTEGER)");
    }
    public static boolean addClass(String day, String subjectName, Time startTime, Time endTime){
        classesDb.execSQL("INSERT INTO classes VALUES('"+day+"','"+subjectName+"','"+startTime+"','"+endTime+"')");
        return true;
    }


    public static List<String> getSubjects(){
        return subs;
    }
    static List<String> days= Arrays.asList("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday");

    public static List<String> getDays(){
        return days;
    }
}
