package edu.neu.ccs.wellness.adcaregiverapp.presentation.garden;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.DrawableUntils;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.UserManager;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.FragmentGardenBinding;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.MainActivity;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.ViewModelFactory;

import static edu.neu.ccs.wellness.adcaregiverapp.common.utils.Constants.USER_GARDEN;

public class GardenFragment extends DaggerFragment {

    private static int SPAN_COUNT = 3;
    private GardenViewModel viewModel;
    private GardenAdapter adapter;
    private FragmentGardenBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private boolean selectionEnabled = false;
    private static final String SELECTION_ENABLED = "selection_enabled";
    private static final String NUMBER_OF_SELECTION = "numberOfSelection";
    private static final String FLOWER_RESOURCE = "flowerResource";
    private static final String STAGE = "stage";
    private DatabaseReference userDataReference;
    private int numberOfSelection;
    private String flowerResource;
    private int stage;
    private Set<Integer> selectedPositions;


    @Inject
    ViewModelFactory viewModelFactory;

    @Inject
    UserManager userManager;

    public static GardenFragment newInstance() {
        GardenFragment fragment = new GardenFragment();
        return fragment;
    }

    public static GardenFragment newInstance(boolean selectionEnabled) {
        Bundle args = new Bundle();
        args.putBoolean(SELECTION_ENABLED, selectionEnabled);
        GardenFragment fragment = new GardenFragment();
        fragment.setArguments(args);
        return fragment;

    }

    public static GardenFragment newInstance(boolean selectionEnabled, int numberOfSelection, String flowerName, int stage) {
        Bundle args = new Bundle();
        args.putBoolean(SELECTION_ENABLED, selectionEnabled);
        args.putInt(NUMBER_OF_SELECTION, numberOfSelection);
        args.putString(FLOWER_RESOURCE, flowerName);
        args.putInt(STAGE, stage);
        GardenFragment fragment = new GardenFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rootDataReference = database.getReference().child(USER_GARDEN);
        userDataReference = rootDataReference.child(String.valueOf(Objects.requireNonNull(userManager.getUser()).getUserId()));

        if (getArguments() != null && getArguments().containsKey(SELECTION_ENABLED)) {
            selectionEnabled = getArguments().getBoolean(SELECTION_ENABLED, false);
        }

        if (getArguments() != null && selectionEnabled) {
            numberOfSelection = getArguments().getInt(NUMBER_OF_SELECTION);
            flowerResource = getArguments().getString(FLOWER_RESOURCE);
            stage = getArguments().getInt(STAGE);
        }

        MainActivity activity = (MainActivity) getActivity();
        if (selectionEnabled && activity != null) {
            activity.hideBottomNavigation();
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(GardenViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_garden, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {


        final MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.setSelectedTab(MainActivity.CurrentTab.MY_GARDEN);
        }

        Observer<GardenViewModel.GardenViewModelResponse> observer = new Observer<GardenViewModel.GardenViewModelResponse>() {
            @Override
            public void onChanged(@Nullable GardenViewModel.GardenViewModelResponse gardenViewModelResponse) {
                binding.gardenProgress.setVisibility(View.GONE);
                binding.gardenRecyclerView.setVisibility(View.VISIBLE);
                switch (gardenViewModelResponse.getStatus()) {
                    case Success:
                        if (mainActivity != null) {
                            mainActivity.startChallengeActivityForResult();
                        }
                        break;

                    case Error:
                        break;
                }

            }
        };

        viewModel.getUserGardenModelMutableLiveData().observe(this, observer);

        if (selectionEnabled) {
            binding.done.setVisibility(View.VISIBLE);
            binding.done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateFirebaseDatabase();
                }
            });
        }
        binding.gardenProgress.setVisibility(View.GONE);
        recyclerView = binding.gardenRecyclerView;
        layoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        if (selectionEnabled) {
            adapter = new GardenAdapter(this.getContext(), selectionEnabled, numberOfSelection,
                    DrawableUntils.getDrawableIdByNameAndStage(getContext(), flowerResource, stage), new GardenFragmentCallBack() {
                @Override
                public void onBlockSelected(Set<Integer> selectedPositions) {
                    setSelectedPositions(selectedPositions);
                }
            });
        } else {
            adapter = new GardenAdapter(this.getContext());
        }
        recyclerView.setAdapter(adapter);
        binding.gardenProgress.setVisibility(View.VISIBLE);
        binding.gradenUsername.setText(Objects.requireNonNull(userManager.getUser()).getUsername() + getString(R.string.garden_heading));
        getFromFireBaseDatabase();
    }

    private void setSelectedPositions(Set<Integer> selectedPositions) {
        this.selectedPositions = selectedPositions;
    }

    private void getFromFireBaseDatabase() {
        final UserGardenModel[][] data = {new UserGardenModel[21]};
        userDataReference.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                int size = (int) mutableData.getChildrenCount();
                if (size >= data[0].length) {
                    data[0] = Arrays.copyOf(data[0], data[0].length + 3);
                }
                for (MutableData children : mutableData.getChildren()) {
                    data[0][Integer.valueOf(children.getKey())] = children.getValue(UserGardenModel.class);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                binding.gardenProgress.setVisibility(View.GONE);
                adapter.setData(data[0]);
            }
        });
    }

    private void updateFirebaseDatabase() {

        binding.gardenProgress.setVisibility(View.VISIBLE);
        binding.gardenRecyclerView.setVisibility(View.GONE);
        if (selectedPositions != null) {
            for (final Integer position : selectedPositions) {
                selectedPositions.remove(position);
                userDataReference.child(String.valueOf(position)).runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        mutableData.setValue(new UserGardenModel(flowerResource, stage, position));
                        return Transaction.success(mutableData);

                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                        if (selectedPositions.size() != 0) {
                            updateFirebaseDatabase();
                        } else {
                            viewModel.setChallengeComplete();
                        }
                    }
                });
                break;
            }
        } else {
            viewModel.setChallengeComplete();
        }

    }

    interface GardenFragmentCallBack {
        void onBlockSelected(Set<Integer> selectedPositions);
    }
}
