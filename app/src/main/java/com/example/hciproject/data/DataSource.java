package com.example.hciproject.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hciproject.objects.Classes;

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
            while (!c.isAfterLast()) {
                subs.add(c.getString(subjectsNameIndex));
                c.moveToNext();
            }
        }
        catch(Exception e){
            Log.i("mine","SQL error");
            e.printStackTrace();
        }
        c.close();
    }

    public static void addSubject(String subjectName){
        subjectsDb.execSQL("INSERT INTO subjects (subjectName) VALUES ('"+subjectName+"')");
        subs.add(subjectName);
    }

    public static void initClasses(Context context){
        classesDb= context.openOrCreateDatabase("classes",Context.MODE_PRIVATE,null);
        classesDb.execSQL("CREATE TABLE IF NOT EXISTS classes(classId VARCHAR,day VARCHAR, subjectName VARCHAR, startTime INTEGER, endTime INTEGER)");
    }
    public static boolean addClass(String classId,String day, String subjectName, String startTime, String endTime){
        classesDb.execSQL("INSERT INTO classes VALUES('"+classId+"','"+day+"','"+subjectName+"','"+startTime+"','"+endTime+"')");
        return true;
    }


    public static List<Classes> getClasses(String day){
        List<Classes> listOfClasses=new ArrayList<>();
        Cursor c=classesDb.rawQuery("SELECT * FROM classes where day='"+day+"' ORDER BY startTime",null);
        c.moveToFirst();
        int subName=c.getColumnIndex("subjectName");
        int start=c.getColumnIndex("startTime");
        int end=c.getColumnIndex("endTime");
        int id=c.getColumnIndex("classId");
        try {
            while (!c.isAfterLast()) {
                String subjectName=c.getString(subName),
                        startTime=c.getString(start),
                        endTime=c.getString(end),
                        classId=c.getString(id);
                listOfClasses.add(new Classes(subjectName,startTime,endTime,classId));
                c.moveToNext();
            }
        }
        catch (Exception e){
            Log.i("mine","SQL error 2");
            e.printStackTrace();
        }
        c.close();
        return listOfClasses;
    }

    public static List<String> getSubjects(){
        return subs;
    }
    static List<String> days= Arrays.asList("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday");

    public static List<String> getDays(){
        return days;
    }
}
