package edu.neu.ccs.wellness.adcaregiverapp;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.ArrayList;

import edu.neu.ccs.wellness.adcaregiverapp.presentation.communityGarden.CommunityGardenFragment;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery.NurseryFragment;

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

        //Bottom Navigation Logic

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

        bottomNavigation.setCurrentItem(2);

        setBottomNavigationOnClickListener(bottomNavigation);

        navigateToCommunityGarden();


    }


    private void setBottomNavigationOnClickListener(AHBottomNavigation bottomNavigation) {

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {

            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 1:

                        Toast.makeText(getBaseContext(), "My Garden Selected", Toast.LENGTH_LONG);
                        break;
                    case 2:
                        navigateToCommunityGarden();
                        break;
                    case 0:
                        navigateToNursery();
                        break;
                    default:
                        navigateToCommunityGarden();

                }
                return true;
            }
        });

    }

    private void navigateToCommunityGarden() {
        // Create new fragment and transaction
        Fragment newFragment = CommunityGardenFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    private void navigateToNursery() {
        Fragment newFragment = NurseryFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}
