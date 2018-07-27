package edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.tutorialFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dagger.android.support.DaggerFragment;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.ExerciseType;

public class TutorialListFragment extends DaggerFragment {


    private ExerciseType exerciseType;
    private static String EXERCISE_TYPE = "exerciseType";
    private RecyclerView recyclerView;
    private TutorialListAdapter adapter;
    private TutorialListViewModel viewModel;


    public static TutorialListFragment newInstance(ExerciseType exerciseType) {

        Bundle args = new Bundle();
        args.putSerializable(EXERCISE_TYPE, exerciseType);
        TutorialListFragment fragment = new TutorialListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            exerciseType = (ExerciseType) getArguments().getSerializable(EXERCISE_TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tutorial_list_dummy, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        TextView textView = view.findViewById(R.id.textView12);
        switch (exerciseType) {
            case BALANCE:
                textView.setText("Balance");
                break;

            case STRENGTH:
                textView.setText("Strength");
                break;

            case ENDURANCE:
                textView.setText("Endurance");
                break;
            case FLEXIBILITY:
                textView.setText("Flexibility");
                break;
        }
    }

}
