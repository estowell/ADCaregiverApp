package edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.neu.ccs.wellness.adcaregiverapp.R;

/**
 * Created by amritanshtripathi on 6/12/18.
 */

public class NurseryFragment extends Fragment {


    public static NurseryFragment newInstance() {

        Bundle args = new Bundle();

        NurseryFragment fragment = new NurseryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nursery,
                container, false);

        init(view);
        return view;
    }

    private void init(View view) {

    }
}
