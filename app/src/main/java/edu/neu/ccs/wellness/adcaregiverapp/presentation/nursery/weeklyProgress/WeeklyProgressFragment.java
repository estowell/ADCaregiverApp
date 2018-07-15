package edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery.weeklyProgress;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.domain.activities.model.Activities;

public class WeeklyProgressFragment extends Fragment {

    private static String BAR_GRAPH_ACTIVITIES = "BAR_GRAPH_ACTIVITIES";
    private ArrayList<Activities> activities;

    public static WeeklyProgressFragment newInstance(ArrayList<Activities> activities) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(BAR_GRAPH_ACTIVITIES, activities);
        WeeklyProgressFragment fragment = new WeeklyProgressFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly_progress, container, false);
        activities = Objects.requireNonNull(getArguments()).getParcelableArrayList(BAR_GRAPH_ACTIVITIES);
        init(view);
        return view;
    }


    private void init(View view) {
        setUpBarChar(view);

        ImageView backButton = view.findViewById(R.id.back_button_weekly_progress);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void setUpBarChar(View view) {
        BarChart chart = view.findViewById(R.id.bar_chart);
        List<BarEntry> entries = new ArrayList<>();
        Collections.sort(activities);
        int xAxis = 1;
        for (Activities activity : activities) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(activity.getDate());
            entries.add(new BarEntry(xAxis, activity.getSteps()));
            xAxis++;
        }

        BarDataSet dataSet = new BarDataSet(entries, "");


        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f);
        chart.setData(data);
        chart.setFitBars(true);
        chart.invalidate();
        chart.isShown();
    }
}
