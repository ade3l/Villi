package com.example.hciproject.data;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    static List<String> subs=new ArrayList<>();

    public static void refreshSubjects(){
        subs.clear();
//        TODO: Populate the subs array with data from sqldb
        subs.add("CAO");
        subs.add("DAA");
        subs.add("HCI");
        subs.add("DBMS");
        subs.add("Chinese");
        subs.add("OS");
    }

    public static List<String> getSubjects(){
        return subs;
    }
}
