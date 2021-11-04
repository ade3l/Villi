package com.example.hciproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;

import com.example.hciproject.data.DataSource;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        DataSource.refreshSubjects();
        setUpNavigation();
    }


    void setUpNavigation(){
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
                if (viewPager.getCurrentItem() == 0 ) {
//                    TODO: hide fab for time table fragment too
                    assignmentsFragment.hideFab();
                }
                viewPager.setCurrentItem(position);
                //It shows up as an error for the first instantiation of the fragment
                try {
//                    TODO: show fab for time table fragment too
                    if (viewPager.getCurrentItem() == 0) {
                        assignmentsFragment.showFab();
                    }
                }
                catch(Exception ignored){ }

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
}