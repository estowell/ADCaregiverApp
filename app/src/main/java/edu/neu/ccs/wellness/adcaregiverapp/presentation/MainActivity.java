package edu.neu.ccs.wellness.adcaregiverapp.presentation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import dagger.android.support.DaggerAppCompatActivity;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.ActivityMainBinding;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.ChallengesActivity;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.communityGarden.CommunityGardenFragment;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.ExercisePagerAdapter;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.garden.GardenFragment;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery.NurseryFragment;

public class MainActivity extends DaggerAppCompatActivity {

    private static final int ACTIVITY_RESULT_CODE = 1;

    public enum CurrentTab {
        NURSERY,
        MY_GARDEN,
        COMMUNITY_GARDEN
    }

    private CurrentTab selectedTab = CurrentTab.NURSERY;
    private ActivityMainBinding binding;

    @Nullable
    private ExercisePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();
    }


    private void init() {

        //Bottom Navigation Logic
        binding.nurseryButton.setTextColor(getResources().getColor(R.color.white));
        binding.nurseryButton.setBackgroundColor(getResources().getColor(R.color.bottom_navigation_color));

        setBottomNavigationOnClickListener();

        navigateToNursery(true);
        test();
        String[] assests = new String[0];
        try {
            assests = this.getAssets().list("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String assest : assests) {
            Log.i("Assest ", assest);
        }
    }

    public void hideBottomNavigation() {

        binding.bottomNavigation.setVisibility(View.GONE);

    }


    public void showBottomNavigation() {

        binding.bottomNavigation.setVisibility(View.VISIBLE);

    }

    public void startChallengeActivity() {
        Intent intent = new Intent(this, ChallengesActivity.class);
        startActivity(intent);
    }

    public void startChallengeActivityForResult() {
        Intent intent = new Intent(this, ChallengesActivity.class);
        startActivityForResult(intent, ACTIVITY_RESULT_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == ACTIVITY_RESULT_CODE) {
            // Make sure the request was successful
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }
    }

    public void setSelectedTab(CurrentTab selectedTab) {
        this.selectedTab = selectedTab;
        updateBottombar();
    }

    private void setBottomNavigationOnClickListener() {

        binding.communityGarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != CurrentTab.COMMUNITY_GARDEN) {
                    selectedTab = CurrentTab.COMMUNITY_GARDEN;
                    updateBottombar();
                    navigateToCommunityGarden();
                }
            }
        });

        binding.nurseryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != CurrentTab.NURSERY) {
                    selectedTab = CurrentTab.NURSERY;
                    updateBottombar();
                    navigateToNursery(false);
                }
            }
        });

        binding.myGarden.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (selectedTab != CurrentTab.MY_GARDEN) {
                    selectedTab = CurrentTab.MY_GARDEN;
                    updateBottombar();
                    navigateToMyGarden();
                }
            }
        });


    }


    private void updateBottombar() {
        switch (selectedTab) {
            case COMMUNITY_GARDEN:
                binding.nurseryButton.setTextColor(getResources().getColor(R.color.black));
                binding.nurseryButton.setBackgroundColor(getResources().getColor(R.color.white));
                binding.myGarden.setTextColor(getResources().getColor(R.color.black));
                binding.myGarden.setBackgroundColor(getResources().getColor(R.color.white));
                binding.communityGarden.setTextColor(getResources().getColor(R.color.white));
                binding.communityGarden.setBackgroundColor(getResources().getColor(R.color.bottom_navigation_color));
                break;

            case NURSERY:
                binding.communityGarden.setTextColor(getResources().getColor(R.color.black));
                binding.communityGarden.setBackgroundColor(getResources().getColor(R.color.white));
                binding.myGarden.setTextColor(getResources().getColor(R.color.black));
                binding.myGarden.setBackgroundColor(getResources().getColor(R.color.white));
                binding.nurseryButton.setTextColor(getResources().getColor(R.color.white));
                binding.nurseryButton.setBackgroundColor(getResources().getColor(R.color.bottom_navigation_color));
                break;

            case MY_GARDEN:
                binding.communityGarden.setTextColor(getResources().getColor(R.color.black));
                binding.communityGarden.setBackgroundColor(getResources().getColor(R.color.white));
                binding.nurseryButton.setTextColor(getResources().getColor(R.color.black));
                binding.nurseryButton.setBackgroundColor(getResources().getColor(R.color.white));
                binding.myGarden.setTextColor(getResources().getColor(R.color.white));
                binding.myGarden.setBackgroundColor(getResources().getColor(R.color.bottom_navigation_color));
                break;
        }
    }


    //TODO: Generalise navigation logic into one function

    private void navigateToCommunityGarden() {
        // Create new fragment and transaction
        Fragment newFragment = CommunityGardenFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    private void navigateToNursery(boolean initialLaunch) {
        Fragment newFragment = NurseryFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        transaction.replace(R.id.fragment_container, newFragment);
        if (!initialLaunch) transaction.addToBackStack(null);

        transaction.commit();
    }

    private void navigateToMyGarden() {
        Fragment newFragment = GardenFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //TODO: Remove ME
    private void test() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("Exercises/images");
        StorageReference insref = storageRef.child("Exercises/instructions");

        StorageReference jsonref = insref.child("flexibility:next.json");
        final long ONE_MEGABYTE = 1024 * 1024;
        final JSONObject[] json = new JSONObject[1];
        jsonref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                try {
                    json[0] = new JSONObject(new String(bytes));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Data for "images/island.jpg" is returns, use this as needed
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (adapter != null) {
            if (!adapter.onBackPressed()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Nullable
    public ExercisePagerAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(@Nullable ExercisePagerAdapter adapter) {
        this.adapter = adapter;
    }
}
