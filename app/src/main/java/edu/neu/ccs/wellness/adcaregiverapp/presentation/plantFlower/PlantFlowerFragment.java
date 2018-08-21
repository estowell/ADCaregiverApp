package edu.neu.ccs.wellness.adcaregiverapp.presentation.plantFlower;

import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dagger.android.support.DaggerFragment;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.DrawableUntils;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.FragmentPlantFlowerBinding;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.CurrentChallenge;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.PassedChallenge;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.MainActivity;

public class PlantFlowerFragment extends DaggerFragment {

    private static final String CURR_CHALLENGE = "currentChallenge";
    private static final String PASSED_CHALLENGE = "passedChallenge";
    private CurrentChallenge currentChallenge;
    private FragmentPlantFlowerBinding binding;
    private PassedChallenge passedChallenge;

    public static PlantFlowerFragment newInstance(@NonNull CurrentChallenge currentChallenge, @NonNull PassedChallenge passedChallenge) {

        Bundle args = new Bundle();
        args.putParcelable(CURR_CHALLENGE, currentChallenge);
        args.putParcelable(PASSED_CHALLENGE, passedChallenge);
        PlantFlowerFragment fragment = new PlantFlowerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentChallenge = getArguments().getParcelable(CURR_CHALLENGE);
            passedChallenge = getArguments().getParcelable(PASSED_CHALLENGE);
        }

        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.hideBottomNavigation();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Todo: add data binding and navigation to garden
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_plant_flower, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        if (currentChallenge != null && passedChallenge != null) {
            int stage = currentChallenge.getSelectedFlower().getStage();
            String flowerName = currentChallenge.getSelectedFlower().getName();
            int exerciseProgress = currentChallenge.getNumberOfExerciseLogs();
            int storiesProgress = currentChallenge.getNumberOfPosts();
            int steps = passedChallenge.getProgress().get(0).getTotalProgress();

            Resources res = getResources();
            binding.steps.setText(res.getQuantityString(R.plurals.numberOfSteps, steps, steps));
            binding.numberOfExerciseLogs.setText(res.getQuantityString(R.plurals.numberOfExercisesLogged, exerciseProgress, exerciseProgress));
            binding.numberOfStoriesPosted.setText(res.getQuantityString(R.plurals.numberOfStoriesPosted, storiesProgress, storiesProgress));
            binding.flowerImage.setImageResource(DrawableUntils.getDrawableIdByNameAndStage(getContext(), flowerName, stage));
            binding.acceptChallenge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Todo:Navigate to my garden without backstack
                }
            });
        } else {
            //TODO: empty state with error message
        }

    }

}
