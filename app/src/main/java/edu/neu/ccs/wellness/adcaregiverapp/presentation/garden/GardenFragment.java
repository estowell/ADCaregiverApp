package edu.neu.ccs.wellness.adcaregiverapp.presentation.garden;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import edu.neu.ccs.wellness.adcaregiverapp.R;
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
    private static String SELECTION_ENABLED = "selection_enabled";
    private DatabaseReference userDataReference;


    @Inject
    ViewModelFactory viewModelFactory;

    @Inject
    UserManager userManager;

    public static GardenFragment newInstance() {
        GardenFragment fragment = new GardenFragment();
        return fragment;
    }

    //TODO: Add number of slections and drawablelist
    public static GardenFragment newInstance(boolean selectionEnabled) {
        Bundle args = new Bundle();
        args.putBoolean(SELECTION_ENABLED, selectionEnabled);
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
            selectionEnabled = getArguments().getBoolean(SELECTION_ENABLED);
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
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.setSelectedTab(MainActivity.CurrentTab.MY_GARDEN);
        }

        binding.gardenProgress.setVisibility(View.GONE);
        recyclerView = binding.gardenRecyclerView;
        layoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GardenAdapter(this.getContext(), selectionEnabled);
        recyclerView.setAdapter(adapter);
        binding.gardenProgress.setVisibility(View.VISIBLE);
        binding.gradenUsername.setText(Objects.requireNonNull(userManager.getUser()).getUsername() + getString(R.string.garden_heading));
        getFromFireBaseDatabase();
    }

    private void getFromFireBaseDatabase() {
        final List<UserGardenModel> data = new ArrayList<>();
        userDataReference.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                for (MutableData children : mutableData.getChildren()) {
                    data.add(children.getValue(UserGardenModel.class));
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                binding.gardenProgress.setVisibility(View.GONE);
                if (data.size() < 21) {
                    while (data.size() != 21) {
                        data.add(new UserGardenModel());
                    }
                    adapter.setData(data);
                } else if (data.size() % 3 != 0) {
                    while (data.size() % 3 != 0) {
                        data.add(new UserGardenModel());
                    }
                }
                adapter.setData(data);
            }
        });
    }

    private void updateFirebaseDatabase() {
        //TODO: Add logic to showdrawable
    }

    interface GardenFragmentCallBack {
        void onBlockSelected(UserGardenModel model);
    }
}
