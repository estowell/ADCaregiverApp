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

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.FragmentGardenBinding;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.ViewModelFactory;

public class GardenFragment extends DaggerFragment {

    private static int SPAN_COUNT = 2;
    private GardenViewModel viewModel;
    private GardenAdapter adapter;
    private FragmentGardenBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Inject
    ViewModelFactory viewModelFactory;

    public static GardenFragment newInstance() {
        GardenFragment fragment = new GardenFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        binding.gardenProgress.setVisibility(View.GONE);
        recyclerView = binding.gardenRecyclerView;
        layoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GardenAdapter();
        recyclerView.setAdapter(adapter);
    }
}
