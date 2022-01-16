package com.example.hciproject;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hciproject.adapters.pagerAdapter;
import com.example.hciproject.data.DataSource;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        setExitSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        window.setSharedElementsUseOverlay(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        DataSource.initSubjects(this);
        DataSource.initClasses(this);
        DataSource.initAssignments(this);
        setUpNavigation();
    }


    void setUpNavigation(){
        ViewPager2 viewPager = findViewById(R.id.content_frame);
        BottomNavigationView navBar=findViewById(R.id.navigation_bar);
//        navBar.setCurrentActiveItem(1);

        FragmentManager manager=getSupportFragmentManager();
        FragmentStateAdapter adapter = new pagerAdapter(manager,getLifecycle());

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1,false);
        navBar.setSelectedItemId(R.id.page_timetable);
        viewPager.setUserInputEnabled(false);

        navBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()){
                    case R.id.page_assignments:
                        viewPager.setCurrentItem(0,true);
                        break;
                    case R.id.page_timetable:
                        viewPager.setCurrentItem(1,true);
                        break;
                    case R.id.page_preferences:
                        viewPager.setCurrentItem(2,true);
                        break;

                }
                return false;
            }
        });


        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
//                navBar.setCurrentActiveItem(position);

            }
        });
    }
}