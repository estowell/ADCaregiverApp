package edu.neu.ccs.wellness.adcaregiverapp;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private AHBottomNavigation bottomNavigation;
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }

        bottomNavigation = findViewById(R.id.bottom_navigation);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.bottom_navigation_left_text), R.color.black);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.bottom_navigation_center_text), R.color.black);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.bottom_navigation_right_text), R.color.black);

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);

        bottomNavigation.addItems(bottomNavigationItems);
        //bottomNavigation.setAccentColor(R.color.black);
    }
}
