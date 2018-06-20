package edu.neu.ccs.wellness.adcaregiverapp.presentation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.ActivityMainBinding;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.communityGarden.CommunityGardenFragment;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery.NurseryFragment;

public class MainActivity extends DaggerAppCompatActivity {

    private enum CurrentTab {
        NURSERY,
        MY_GARDEN,
        COMMUNITY_GARDEN
    }

    private CurrentTab selectedTab = CurrentTab.COMMUNITY_GARDEN;


    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();
    }


    private void init() {

        //Bottom Navigation Logic
        binding.communityGarden.setTextColor(getResources().getColor(R.color.white));
        binding.communityGarden.setBackgroundColor(getResources().getColor(R.color.bottom_navigation_color));

        setBottomNavigationOnClickListener();

        navigateToCommunityGarden();

    }

    private void initialiseDependencies() {
//        DaggerApplicationComponent()
    }

    private void setBottomNavigationOnClickListener() {

        binding.communityGarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != CurrentTab.COMMUNITY_GARDEN) {
                    selectedTab = CurrentTab.COMMUNITY_GARDEN;
                    binding.nurseryButton.setTextColor(getResources().getColor(R.color.black));
                    binding.nurseryButton.setBackgroundColor(getResources().getColor(R.color.white));
                    binding.myGarden.setTextColor(getResources().getColor(R.color.black));
                    binding.myGarden.setBackgroundColor(getResources().getColor(R.color.white));
                    binding.communityGarden.setTextColor(getResources().getColor(R.color.white));
                    binding.communityGarden.setBackgroundColor(getResources().getColor(R.color.bottom_navigation_color));
                    navigateToCommunityGarden();
                }
            }
        });

        binding.nurseryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab = CurrentTab.NURSERY;
                binding.communityGarden.setTextColor(getResources().getColor(R.color.black));
                binding.communityGarden.setBackgroundColor(getResources().getColor(R.color.white));
                binding.myGarden.setTextColor(getResources().getColor(R.color.black));
                binding.myGarden.setBackgroundColor(getResources().getColor(R.color.white));
                binding.nurseryButton.setTextColor(getResources().getColor(R.color.white));
                binding.nurseryButton.setBackgroundColor(getResources().getColor(R.color.bottom_navigation_color));
                navigateToNursery();
            }
        });

        binding.myGarden.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectedTab = CurrentTab.MY_GARDEN;
                binding.communityGarden.setTextColor(getResources().getColor(R.color.black));
                binding.communityGarden.setBackgroundColor(getResources().getColor(R.color.white));
                binding.nurseryButton.setTextColor(getResources().getColor(R.color.black));
                binding.nurseryButton.setBackgroundColor(getResources().getColor(R.color.white));
                binding.myGarden.setTextColor(getResources().getColor(R.color.white));
                binding.myGarden.setBackgroundColor(getResources().getColor(R.color.bottom_navigation_color));
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
