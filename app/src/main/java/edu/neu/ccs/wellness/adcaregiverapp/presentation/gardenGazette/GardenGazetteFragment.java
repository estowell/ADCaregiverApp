package edu.neu.ccs.wellness.adcaregiverapp.presentation.gardenGazette;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.FragmentGardenGazetteBinding;
import edu.neu.ccs.wellness.adcaregiverapp.domain.nursery.model.StoryPost;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery.dialogs.ShareStoriesDialog;

import static edu.neu.ccs.wellness.adcaregiverapp.common.utils.Constants.USER_STORIES;

public class GardenGazetteFragment extends Fragment {

    private RecyclerView recyclerView;
    private GardenGazetteAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FragmentGardenGazetteBinding binding;
    private GardenGazetteViewModel viewModel;

    private static final String PROGRESS_VALUE = "progress_value";
    private static final String USER_NAME = "username";
    private static final String USER_ID = "USER_ID";
    private Integer donutProgress = 0;
    private String userName;
    private int userId;

    public static GardenGazetteFragment newInstance(Integer progress, String userName, int userId) {

        Bundle args = new Bundle();
        args.putInt(PROGRESS_VALUE, progress);
        args.putString(USER_NAME, userName);
        args.putInt(USER_ID, userId);
        GardenGazetteFragment fragment = new GardenGazetteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_garden_gazette, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query myRef = database.getReference().child(USER_STORIES).orderByKey();
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(PROGRESS_VALUE)) {
            userName = bundle.getString(USER_NAME);
            userId = bundle.getInt(USER_ID);
            donutProgress = bundle.getInt(PROGRESS_VALUE);
        }

        init();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                binding.gardenGazetteProgress.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                List<StoryPost> result = new ArrayList<>();
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    StoryPost post = postSnapShot.getValue(StoryPost.class);
                    result.add(post);
                }
                Collections.reverse(result);
                adapter.setResult(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return binding.getRoot();
    }


    private void init() {
        if (binding != null) {
            binding.gardenGazetteProgress.setVisibility(View.VISIBLE);
            recyclerView = binding.recyclerView;
            layoutManager = new LinearLayoutManager(this.getActivity().getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new GardenGazetteAdapter();
            adapter.setHasStableIds(true);
            recyclerView.setAdapter(adapter);

            binding.back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().popBackStack();
                }
            });

            binding.addNewPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showNewPostDialog();
                }
            });
        } else {
            Log.d(this.getClass().getSimpleName(), "binding is null");
        }

    }


    private void showNewPostDialog() {

        ShareStoriesDialog dialog = ShareStoriesDialog.newInstance(donutProgress, userName, userId);
        assert getFragmentManager() != null;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        dialog.show(ft, ShareStoriesDialog.class.getSimpleName());

    }
}
