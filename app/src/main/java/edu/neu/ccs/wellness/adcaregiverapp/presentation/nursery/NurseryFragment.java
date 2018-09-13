package edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.DrawableUntils;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.UserManager;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.FragmentNurseryBinding;
import edu.neu.ccs.wellness.adcaregiverapp.domain.login.model.User;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.AvailableChallenges;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.CurrentChallenge;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.PassedChallenge;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.Progress;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.RunningChallenges;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.SelectedFlower;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.UnlockedFlowersModel;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.MainActivity;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.ViewModelFactory;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.ExerciseFragment;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.gardenGazette.GardenGazetteFragment;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery.weeklyProgress.WeeklyProgressFragment;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.plantFlower.PlantFlowerFragment;

import static edu.neu.ccs.wellness.adcaregiverapp.common.utils.Constants.CURRENT_CHALLENGE;
import static edu.neu.ccs.wellness.adcaregiverapp.common.utils.Constants.UNLOCKED_FLOWERS;

/**
 * Created by amritanshtripathi on 6/12/18.
 */
public class NurseryFragment extends DaggerFragment {

    private FragmentNurseryBinding binding;
    private NurseryViewModel viewModel;
    private User user;
    private Integer storiesProgress = 0;
    private MainActivity activity;
    private CurrentChallenge currentChallenge;
    @Inject
    ViewModelFactory viewModelFactory;

    @Inject
    UserManager userManager;

    public static NurseryFragment newInstance() {

        Bundle args = new Bundle();

        NurseryFragment fragment = new NurseryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NurseryViewModel.class);
        activity = (MainActivity) getActivity();
        getUser();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nursery, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        binding.nurseryProgressBar.setVisibility(View.VISIBLE);
        if (activity != null) {
            activity.showBottomNavigation();
            activity.setSelectedTab(MainActivity.CurrentTab.NURSERY);
        }

        Observer<NurseryViewModel.NurseryViewModelResponse> nurseryViewModelResponseObserver = new Observer<NurseryViewModel.NurseryViewModelResponse>() {
            @Override
            public void onChanged(@Nullable NurseryViewModel.NurseryViewModelResponse nurseryViewModelResponse) {
                switch (nurseryViewModelResponse.getStatus()) {
                    case AVAILABLE:
                        onStatusAvailable(nurseryViewModelResponse.getAvailableChallenges());
                        break;
                    case RUNNING:
                        onStatusRunning(nurseryViewModelResponse.getRunningChallenges());
                        break;
                    case PASSED:
                        onChallengePassed(nurseryViewModelResponse.getPassedChallenge());
                        break;
                    case ERROR:
                        Toast.makeText(getContext(), nurseryViewModelResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        viewModel.getResponseMutableLiveData().observe(this, nurseryViewModelResponseObserver);
        viewModel.getChallenges();

        binding.stories.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showExercisesFragment();
            }
        });

        binding.exerciseProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStoriesDialog();
            }
        });


    }

    //TODO: Improve this code
    private void onStatusAvailable(AvailableChallenges availableChallenges) {
        binding.nurseryProgressBar.setVisibility(View.GONE);
        binding.selectNewChallenge.setVisibility(View.VISIBLE);
        binding.flowerImage.setVisibility(View.GONE);
        updateProgressFromFireBase();
        updateCurrentChallengeOnFireBase(false, false, 1);
        binding.selectNewChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToChallengeActivity();
            }
        });

    }

    private void onChallengePassed(final PassedChallenge passedChallenge) {
        //TODO:Navigate to Passed
        final int stage = getStage(passedChallenge.getProgress().get(0));
        int userId = Objects.requireNonNull(userManager.getUser()).getUserId();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference().child(CURRENT_CHALLENGE).child(String.valueOf(userId));

        databaseReference.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                currentChallenge = mutableData.getValue(CurrentChallenge.class);
                if (currentChallenge != null) {
                    currentChallenge.setRunning(false);
                    currentChallenge.setPassed(true);
                    SelectedFlower selectedFlower = currentChallenge.getSelectedFlower();
                    selectedFlower.setStage(stage);
                    currentChallenge.setSelectedFlower(selectedFlower);
                    mutableData.setValue(currentChallenge);
                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                binding.nurseryProgressBar.setVisibility(View.GONE);
                PlantFlowerFragment fragment = PlantFlowerFragment.newInstance(Objects.requireNonNull(dataSnapshot).getValue(CurrentChallenge.class), passedChallenge);
                FragmentTransaction ft = Objects.requireNonNull(getFragmentManager()).beginTransaction();
                ft.replace(R.id.fragment_container, fragment);
                ft.commit();
            }
        });
    }

    private void onStatusRunning(RunningChallenges runningChallenges) {
        updateProgressFromFireBase();
        int stage = getStage(runningChallenges.getProgress().get(0));
        updateStepsProgressBar(runningChallenges);
        updateCurrentChallengeOnFireBase(true, false, stage);
    }

    private void updateStepsProgressBar(final RunningChallenges runningChallenges) {
        int progressSize = 0;
        if (runningChallenges.getProgress().get(0).getProgressPercent() != null) {
            progressSize = Objects.requireNonNull(runningChallenges.getProgress().get(0).getProgressPercent()).size();
        }
        if (progressSize != 0) {
            double todayProgress = 0;

            if (runningChallenges.getProgress().get(0).getProgressPercent() != null) {
                todayProgress = Objects.requireNonNull(runningChallenges.getProgress().get(0).getProgressPercent()).get(progressSize - 1);
            }

            binding.stepsBar.setProgress((int) Math.round(todayProgress));

            binding.stepsBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToWeeklyProgress(runningChallenges.getProgress().get(0).getProgress());
                }
            });
        } else {
            binding.stepsBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToWeeklyProgress(new ArrayList<Integer>());
                }
            });
        }

    }

    private int getStage(Progress progress) {
        int stage = 1;
        if (progress.getProgressAchieved() != null) {
            for (boolean status : Objects.requireNonNull(progress.getProgressAchieved())) {
                if (status) {
                    stage++;
                }
            }
        }
        return stage;
    }


    private void navigateToChallengeActivity() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.startChallengeActivity();
        }
    }

    private void navigateToWeeklyProgress(ArrayList<Integer> dailySteps) {
//        ArrayList<Activities> activities = (ArrayList<Activities>) viewModel.getActivities();
        Fragment fragment = WeeklyProgressFragment.newInstance(dailySteps);
        FragmentTransaction ft = Objects.requireNonNull(getFragmentManager()).beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();

    }

    private void showStoriesDialog() {
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = Objects.requireNonNull(fragmentManager).beginTransaction();

        transaction.replace(R.id.fragment_container, GardenGazetteFragment.newInstance(storiesProgress, user.getUsername(), user.getUserId()));
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void showExercisesFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = Objects.requireNonNull(fragmentManager).beginTransaction();
        ExerciseFragment fragment = ExerciseFragment.newInstance();

        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void getUser() {
        user = userManager.getUser();
    }


    private void updateProgressFromFireBase() {
        int userId = Objects.requireNonNull(userManager.getUser()).getUserId();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference().child(CURRENT_CHALLENGE).child(String.valueOf(userId));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentChallenge = dataSnapshot.getValue(CurrentChallenge.class);
                if (currentChallenge != null && currentChallenge.isRunning()) {
                    int numberOfPosts = currentChallenge.getNumberOfPosts();
                    int numberOfExerciseLogs = currentChallenge.getNumberOfExerciseLogs();
                    updateStoriesProgress(numberOfPosts);
                    updateExerciseProgress(numberOfExerciseLogs);


                } else {
                    storiesProgress = 0;
                    binding.exerciseProgress.setProgress(0);
                    binding.stories.setProgress(0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateExerciseProgress(int numberOfExerciseLogs) {
        float percentage = (float) ((numberOfExerciseLogs / 7.00) * 100);
        binding.stories.setProgress((int) percentage);
    }

    private void updateStoriesProgress(int numberOfPosts) {
        if (numberOfPosts == 0) {
            storiesProgress = 0;
            binding.exerciseProgress.setProgress(0);
        } else if (numberOfPosts % 2 == 0) {
            storiesProgress = 100;
            binding.exerciseProgress.setProgress(100);
            if (!currentChallenge.isFlowerUnlocked()) {
                updateUnlockedFlowersOnFireBase();
            }
        } else {
            storiesProgress = 50;
            binding.exerciseProgress.setProgress(50);
        }
    }

    private void updateCurrentChallengeOnFireBase(final boolean isRunning, final boolean passed, final int flowerStage) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child(CURRENT_CHALLENGE).child(String.valueOf(Objects.requireNonNull(userManager.getUser()).getUserId()));
        reference.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                CurrentChallenge dataValue = mutableData.getValue(CurrentChallenge.class);
                if (dataValue != null) {
                    Objects.requireNonNull(dataValue).setPassed(passed);

                    dataValue.setRunning(isRunning);

                    SelectedFlower selectedFlower = dataValue.getSelectedFlower();
                    selectedFlower.setStage(flowerStage);
                    dataValue.setSelectedFlower(selectedFlower);
                    mutableData.setValue(dataValue);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                currentChallenge = dataSnapshot.getValue(CurrentChallenge.class);

                //update flower image if challenge is running
                binding.nurseryProgressBar.setVisibility(View.GONE);
                if (isRunning) {
                    binding.selectNewChallenge.setVisibility(View.GONE);
                    binding.flowerImage.setVisibility(View.VISIBLE);
                    binding.flowerImage.setImageResource(DrawableUntils.getDrawableIdByNameAndStage(getContext(),
                            currentChallenge.getSelectedFlower().getName(), currentChallenge.getSelectedFlower().getStage()));
                }
            }
        });
    }

    private void updateCurrentChallengeOnFireBase(final boolean flowerUnlocked) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child(CURRENT_CHALLENGE).child(String.valueOf(Objects.requireNonNull(userManager.getUser()).getUserId()));
        reference.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                CurrentChallenge dataValue = mutableData.getValue(CurrentChallenge.class);
                if (dataValue != null) {

                    dataValue.setFlowerUnlocked(flowerUnlocked);
                    mutableData.setValue(dataValue);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

            }
        });
    }

    public void updateUnlockedFlowersOnFireBase() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference().child(UNLOCKED_FLOWERS).child(String.valueOf(Objects.requireNonNull(userManager.getUser()).getUserId()));
        databaseReference.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                UnlockedFlowersModel model = dataSnapshot.getValue(UnlockedFlowersModel.class);
                if (!model.isWeek1()) {
                    model.setWeek1(true);
                } else if (!model.isWeek2()) {
                    model.setWeek2(true);
                } else {
                    model.setWeek3(true);
                }
                databaseReference.setValue(model);
                updateCurrentChallengeOnFireBase(true);
            }
        });
    }
}
