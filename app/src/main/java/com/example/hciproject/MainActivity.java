package com.example.hciproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    static List<String> subs=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateSubs();
        ViewPager2 viewPager = findViewById(R.id.content_frame);
        BubbleNavigationLinearView navBar=findViewById(R.id.navigation_bar);
        navBar.setCurrentActiveItem(1);

        FragmentManager manager=getSupportFragmentManager();
        FragmentStateAdapter adapter = new pagerAdapter(manager,getLifecycle());

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1,false);

        viewPager.setUserInputEnabled(false);
        navBar.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                switch(viewPager.getCurrentItem()){
                    case 0: assignmentsFragment.hideFab();break;
                }
                viewPager.setCurrentItem(position);
                //It shows up as an error for the first instantiation of the fragment
                try {
                    switch(viewPager.getCurrentItem()){
                        case 0: assignmentsFragment.showFab();break;
                    }
                }
                catch(Exception e){ }

            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                navBar.setCurrentActiveItem(position);

            }
        });



    }
    void populateSubs(){
        subs.clear();
//        TODO: Populate the subs array with data from sqldb
        subs.add("CAO");
        subs.add("DAA");
        subs.add("HCI");
        subs.add("DBMS");
        subs.add("Chinese");
        subs.add("OS");
    }
}