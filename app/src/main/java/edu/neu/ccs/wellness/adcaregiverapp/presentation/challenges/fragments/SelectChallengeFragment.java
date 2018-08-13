package edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.DrawableUntils;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.UserManager;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.FragmentSelectChallengeBinding;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.AvailableChallenges;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.UnitChallenge;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.UnlockedFlowersModel;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.ViewModelFactory;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.challenges.ChallengesViewModel;

import static edu.neu.ccs.wellness.adcaregiverapp.common.utils.Constants.UNLOCKED_FLOWERS;

public class SelectChallengeFragment extends DaggerFragment {

    private FragmentSelectChallengeBinding binding;
    private ChallengesViewModel viewModel;
    private ChallengeSelected challengeSelected;
    private UnitChallenge currUnitChallenge;
    private List<UnlockedFlowersModel> unlockedFlowersModelList;


    public enum ChallengeSelected {
        LESS_THAN,
        MORE_THAN,
        SAME_AS
    }

    @Inject
    ViewModelFactory viewModelFactory;

    @Inject
    UserManager userManager;

    public static SelectChallengeFragment newInstance() {

        Bundle args = new Bundle();

        SelectChallengeFragment fragment = new SelectChallengeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), viewModelFactory).get(ChallengesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_challenge, container, false);

        init();
        return binding.getRoot();
    }

    private void init() {

        binding.selectNewChallengeProgressBar.setVisibility(View.VISIBLE);
        binding.challengesContainer.setVisibility(View.GONE);
        getUnlockedFlowerStatus();

        Observer<AvailableChallenges> availableChallengesObserver = new Observer<AvailableChallenges>() {
            @Override
            public void onChanged(@Nullable AvailableChallenges challenges) {
                setOnClickListners(challenges);
            }
        };

        Observer<String> errorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.selectNewChallengeProgressBar.setVisibility(View.GONE);
                binding.challengesContainer.setVisibility(View.VISIBLE);

                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            }
        };

        viewModel.getAvailableChallengesLiveData().observe(this, availableChallengesObserver);
        viewModel.getErrorLiveData().observe(this, errorObserver);
        viewModel.fetchAvailableChallenges();

    }

    private void setOnClickListners(final AvailableChallenges challenges) {
        binding.lessThan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnlockedFlowersModel model = unlockedFlowersModelList.get(0);
                currUnitChallenge = challenges.getChallenges().get(0);
                challengeSelected = ChallengeSelected.LESS_THAN;
                if (isFlowerUnlocked(model)) {
                    updateView();
                } else {
                    navigateToAcceptChallengeFragment(getResources().getResourceName(R.drawable.week1_flower_easy_1_7));
                }
            }
        });
        binding.MoreThan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UnlockedFlowersModel model = unlockedFlowersModelList.get(2);
                currUnitChallenge = challenges.getChallenges().get(2);
                challengeSelected = ChallengeSelected.MORE_THAN;
                if (isFlowerUnlocked(model)) {
                    updateView();
                } else {
                    navigateToAcceptChallengeFragment(getResources().getResourceName(R.drawable.week1_flower_hard_1_7));
                }
            }
        });

        binding.same.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnlockedFlowersModel model = unlockedFlowersModelList.get(1);
                currUnitChallenge = challenges.getChallenges().get(1);
                challengeSelected = ChallengeSelected.SAME_AS;
                if (isFlowerUnlocked(model)) {
                    updateView();
                } else {
                    navigateToAcceptChallengeFragment(getResources().getResourceName(R.drawable.week1_flower_medium_1_7));
                }
            }
        });
    }

    private void updateView() {
        switch (challengeSelected) {
            case LESS_THAN:
                List<ImageView> imageViews = Arrays.asList(new ImageView[]{binding.lessThanImage1, binding.lessThanImage2, binding.lessThanImage3});
                setflowers("easy", imageViews);
                binding.subheader.setText("This week, my step goal is LESS THAN last week");
                binding.lessThanImagesContainer.setVisibility(View.VISIBLE);
                binding.MoreThanImagesContainer.setVisibility(View.GONE);
                binding.sameImagesContainer.setVisibility(View.GONE);
                break;
            case SAME_AS:
                imageViews = Arrays.asList(new ImageView[]{binding.sameImage1, binding.sameImage2, binding.sameImage3});
                setflowers("medium", imageViews);
                binding.subheader.setText("This week, my step goal is SAME AS last week");
                binding.lessThanImagesContainer.setVisibility(View.GONE);
                binding.MoreThanImagesContainer.setVisibility(View.GONE);
                binding.sameImagesContainer.setVisibility(View.VISIBLE);
                break;
            case MORE_THAN:
                imageViews = Arrays.asList(new ImageView[]{binding.MoreThanImage1, binding.MoreThanImage1, binding.MoreThanImage1});
                setflowers("hard", imageViews);
                binding.subheader.setText("This week, my step goal is MORE THAN last week");
                binding.lessThanImagesContainer.setVisibility(View.GONE);
                binding.MoreThanImagesContainer.setVisibility(View.VISIBLE);
                binding.sameImagesContainer.setVisibility(View.GONE);
                break;
        }
    }

    private void setflowers(String challengetype, List<ImageView> imageViews) {
        for (UnlockedFlowersModel model : unlockedFlowersModelList) {
            if (model.isWeek1()) {
                final String imageName = "edu.neu.ccs.wellness.adcaregiverapp:drawable/week2_flower_" + challengetype + "_1_7";
                ImageView imageView = imageViews.get(0);
                imageView.setImageResource(DrawableUntils.getDrawableIdByName(getContext(),
                        imageName));
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        navigateToAcceptChallengeFragment(imageName);
                    }
                });
            }

            if (model.isWeek2()) {
                final String imageName = "edu.neu.ccs.wellness.adcaregiverapp:drawable-v24/week3_flower_" + challengetype + "_1_7";
                ImageView imageView = imageViews.get(1);
                imageView.setImageResource(DrawableUntils.getDrawableIdByName(getContext(),
                        imageName));
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        navigateToAcceptChallengeFragment(imageName);
                    }
                });
            }

            if (model.isWeek3()) {
                final String imageName = "edu.neu.ccs.wellness.adcaregiverapp:drawable/week4_flower_" + challengetype + "_1_7";
                ImageView imageView = imageViews.get(2);
                imageView.setImageResource(DrawableUntils.getDrawableIdByName(getContext(),
                        imageName));
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        navigateToAcceptChallengeFragment(imageName);
                    }
                });
            }
        }
    }


    private boolean isFlowerUnlocked(UnlockedFlowersModel model) {
        return model.isWeek1() || model.isWeek2() || model.isWeek3();
    }

    private void navigateToAcceptChallengeFragment(String imageName) {

        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        AcceptChallengeFragment acceptChallengeFragment = AcceptChallengeFragment.newInstance(currUnitChallenge, imageName);
        ft.replace(R.id.challenges_container, acceptChallengeFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void getUnlockedFlowerStatus() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceEasy = database.getReference().child(UNLOCKED_FLOWERS)
                .child(String.valueOf(Objects.requireNonNull(userManager.getUser()).getUserId())).child("easy");
        final DatabaseReference databaseReferenceMedium = database.getReference().child(UNLOCKED_FLOWERS)
                .child(String.valueOf(Objects.requireNonNull(userManager.getUser()).getUserId())).child("medium");
        final DatabaseReference databaseReferenceHard = database.getReference().child(UNLOCKED_FLOWERS)
                .child(String.valueOf(Objects.requireNonNull(userManager.getUser()).getUserId())).child("hard");
        unlockedFlowersModelList = new ArrayList<>();
        getFlowerStatusForLessThan(databaseReferenceEasy, databaseReferenceMedium, databaseReferenceHard);

    }

    private void getFlowerStatusForLessThan(DatabaseReference databaseReferenceEasy,
                                            final DatabaseReference databaseReferenceMedium,
                                            final DatabaseReference databaseReferenceHard) {

        databaseReferenceEasy.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                UnlockedFlowersModel model = new UnlockedFlowersModel(false, false, false);
                if (mutableData.getValue(UnlockedFlowersModel.class) == null) {
                    mutableData.setValue(model);

                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                unlockedFlowersModelList.add(0, dataSnapshot.getValue(UnlockedFlowersModel.class));
                getFlowerStatusForSameAs(databaseReferenceMedium, databaseReferenceHard);
            }
        });

    }

    private void getFlowerStatusForSameAs(DatabaseReference databaseReferenceMedium,
                                          final DatabaseReference databaseReferenceHard) {

        databaseReferenceMedium.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                UnlockedFlowersModel model = new UnlockedFlowersModel(false, false, false);
                if (mutableData.getValue(UnlockedFlowersModel.class) == null) {
                    mutableData.setValue(model);

                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                unlockedFlowersModelList.add(1, dataSnapshot.getValue(UnlockedFlowersModel.class));
                getFlowerStatusForMoreThan(databaseReferenceHard);
            }
        });

    }

    private void getFlowerStatusForMoreThan(DatabaseReference databaseReferenceHard) {

        databaseReferenceHard.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                UnlockedFlowersModel model = new UnlockedFlowersModel(false, false, false);
                if (mutableData.getValue(UnlockedFlowersModel.class) == null) {
                    mutableData.setValue(model);
                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                unlockedFlowersModelList.add(2, dataSnapshot.getValue(UnlockedFlowersModel.class));
                binding.selectNewChallengeProgressBar.setVisibility(View.GONE);
                binding.challengesContainer.setVisibility(View.VISIBLE);
            }
        });

    }
}
