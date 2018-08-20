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
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.MainActivity;

public class WeeklyProgressFragment extends Fragment {

    private static String BAR_GRAPH_ACTIVITIES = "BAR_GRAPH_ACTIVITIES";
    private ArrayList<Integer> activities;

    public static WeeklyProgressFragment newInstance(ArrayList<Integer> activities) {

        Bundle args = new Bundle();
        args.putIntegerArrayList(BAR_GRAPH_ACTIVITIES, activities);
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
        activities = Objects.requireNonNull(getArguments()).getIntegerArrayList(BAR_GRAPH_ACTIVITIES);
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
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.hideBottomNavigation();
        }
    }

    private void setUpBarChar(View view) {
        if (activities.size() == 0) {
            view.findViewById(R.id.constraint_group).setVisibility(View.GONE);
            view.findViewById(R.id.empty_state).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.empty_state).setVisibility(View.GONE);
            view.findViewById(R.id.constraint_group).setVisibility(View.VISIBLE);

            BarChart chart = view.findViewById(R.id.bar_chart);
            List<BarEntry> entries = new ArrayList<>();
            int xAxisValue = 1;
            for (Integer activity : activities) {
                entries.add(new BarEntry(xAxisValue, activity));
                xAxisValue++;
            }

            BarDataSet dataSet = new BarDataSet(entries, "");

            BarData data = new BarData(dataSet);
            data.setBarWidth(0.9f);
            chart.setData(data);
            chart.setFitBars(true);
            chart.setBackgroundColor(getResources().getColor(R.color.white));
            chart.setAutoScaleMinMaxEnabled(false);
            chart.setTouchEnabled(false);
            Description description = new Description();
            description.setText("");

            chart.setDescription(description);
            YAxis left = chart.getAxisLeft();
            left.setDrawLabels(false); // no axis labels
            left.setDrawAxisLine(false); // no axis line
            left.setDrawGridLines(false); // no grid lines
            left.setDrawZeroLine(true); // draw a zero line
            chart.getAxisRight().setEnabled(false);

            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);

            xAxis.setDrawAxisLine(false);
            xAxis.setDrawGridLines(false);
            chart.invalidate();
            chart.isShown();
        }
//
//        BarData data = new BarData(dataSet);
//        data.setBarWidth(0.9f);
//        chart.setData(data);
//        chart.setFitBars(true);
//        chart.invalidate();
//        chart.isShown();
    }
}
