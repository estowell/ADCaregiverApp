package edu.neu.ccs.wellness.adcaregiverapp.presentation.communityGarden;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.neu.ccs.wellness.adcaregiverapp.R;

/**
 * Created by amritanshtripathi on 6/10/18.
 */

public class CommunityGardenFragment extends Fragment {

    private static int SPAN_COUNT = 2;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public static CommunityGardenFragment newInstance() {

        Bundle args = new Bundle();

        CommunityGardenFragment fragment = new CommunityGardenFragment();
        fragment.setArguments(args);
        return fragment;
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
        recyclerView = view.findViewById(R.id.garden_recycler_view);
        layoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommunityGardenAdapter(mock);
        recyclerView.setAdapter(adapter);
    }


    //TODO: Remove MockData
    List<String> mock = new ArrayList() {{
        add("Amy");
        add("John");
        add("Rob");
        add("Amy");
        add("John");
        add("Rob");
        add("Amy");
        add("John");
        add("Rob");
    }};


}
