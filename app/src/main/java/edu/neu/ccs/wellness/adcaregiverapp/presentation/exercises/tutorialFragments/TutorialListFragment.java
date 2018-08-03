package edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.tutorialFragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.UserManager;
import edu.neu.ccs.wellness.adcaregiverapp.domain.exercise.usecase.model.Exercise;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.LogExerciseModel;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.ViewModelFactory;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.ExerciseType;

import static edu.neu.ccs.wellness.adcaregiverapp.common.utils.Constants.EXERCISE_LOG;
import static edu.neu.ccs.wellness.adcaregiverapp.common.utils.Constants.TOTAL_LOG_COUNT;

public class TutorialListFragment extends DaggerFragment {

    private ExerciseType exerciseType;
    private static String EXERCISE_TYPE = "exerciseType";
    private RecyclerView recyclerView;
    private TutorialListAdapter adapter;
    private TutorialListViewModel viewModel;
    @Inject
    ViewModelFactory viewModelFactory;
    @Inject
    UserManager userManager;

    @Inject
    Context context;

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

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TutorialListViewModel.class);
        adapter = new TutorialListAdapter(new TutorialListCallBack() {
            @Override
            public void onLogButtonClicked() {
                logExercise();
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tutorial_list, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView = view.findViewById(R.id.exercise_list_recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        init(view);
        return view;
    }

    private void init(View view) {

        Observer<List<Exercise>> observer = new Observer<List<Exercise>>() {
            @Override
            public void onChanged(@Nullable List<Exercise> exercises) {
                adapter.setData(exercises);
            }
        };

        Observer<String> errorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }
        };

        viewModel.getGetExerciseObserver().observe(this, observer);

        viewModel.getErrorLiverData().observe(this, errorObserver);

        if (exerciseType != null) {
            viewModel.getTutorials(exerciseType);
        }

        TextView textView = view.findViewById(R.id.exercise_type);

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


    private void logExercise() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        DatabaseReference logCount = databaseReference.child(TOTAL_LOG_COUNT);
        final DatabaseReference logRef = databaseReference.child(EXERCISE_LOG);

        final Integer[] count = {0};
        logCount.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                count[0] = mutableData.getValue(Integer.class);
                if (count[0] == null) {
                    count[0] = 1;
                } else {
                    ++count[0];
                }
                mutableData.setValue(count[0]);

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                DatabaseReference ref = logRef.child(String.valueOf(count[0]));
                ref.setValue(new LogExerciseModel(userManager.getUser().getUserId()));
            }
        });
    }


    interface TutorialListCallBack {

        void onLogButtonClicked();
    }
}
