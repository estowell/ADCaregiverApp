package edu.neu.ccs.wellness.adcaregiverapp.presentation.communityGarden;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.Member;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.MainActivity;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.ViewModelFactory;

/**
 * Created by amritanshtripathi on 6/10/18.
 */

public class CommunityGardenFragment extends DaggerFragment {

    private static int SPAN_COUNT = 2;
    private RecyclerView recyclerView;
    private CommunityGardenAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private CommunityGardenViewModel viewModel;
    private ProgressBar progressBar;
    @Inject
    ViewModelFactory viewModelFactory;

    public static CommunityGardenFragment newInstance() {

        Bundle args = new Bundle();

        CommunityGardenFragment fragment = new CommunityGardenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CommunityGardenViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_garden,
                container, false);

        init(view);
        return view;

    }

    private void init(View view) {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.setSelectedTab(MainActivity.CurrentTab.COMMUNITY_GARDEN);
            mainActivity.showBottomNavigation();
        }

        recyclerView = view.findViewById(R.id.community_garden_recycler_view);
        layoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommunityGardenAdapter((MainActivity) getActivity());
        recyclerView.setAdapter(adapter);
        progressBar = view.findViewById(R.id.communityGarden_progressbar);
        initialiseLiveDataObservers();

    }


    private void initialiseLiveDataObservers() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        Observer<List<Member>> memberObserver = new Observer<List<Member>>() {
            @Override
            public void onChanged(@Nullable List<Member> members) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                adapter.setData(members);
            }
        };


        Observer<String> errorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        };

        viewModel.getErrorLiveData().observe(this, errorObserver);
        viewModel.getMembersLiveData().observe(this, memberObserver);
        viewModel.getMembers();
    }

}
