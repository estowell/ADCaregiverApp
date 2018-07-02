package edu.neu.ccs.wellness.adcaregiverapp.presentation.gardenGazette;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.FragmentGardenGazetteBinding;
import edu.neu.ccs.wellness.adcaregiverapp.domain.Nursery.model.StoryPost;

public class GardenGazetteFragment extends Fragment {

    private RecyclerView recyclerView;
    private GardenGazetteAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FragmentGardenGazetteBinding binding;
    private GardenGazetteViewModel viewModel;
    private final List<StoryPost> result = new ArrayList<>();

    public static GardenGazetteFragment newInstance() {

        Bundle args = new Bundle();

        GardenGazetteFragment fragment = new GardenGazetteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_garden_gazette, container, false);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("user-stories");


        init();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    StoryPost post = postSnapShot.getValue(StoryPost.class);
                    result.add(post);
                }
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
            recyclerView = binding.recyclerView;
            layoutManager = new LinearLayoutManager(this.getActivity().getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new GardenGazetteAdapter();
            adapter.setHasStableIds(true);
            recyclerView.setAdapter(adapter);
        } else {
            Log.d(this.getClass().getSimpleName(), "binding is null");
        }

    }
}
