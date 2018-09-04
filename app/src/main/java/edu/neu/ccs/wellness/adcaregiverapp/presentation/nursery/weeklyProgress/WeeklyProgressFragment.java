package edu.neu.ccs.wellness.adcaregiverapp.presentation.nursery.weeklyProgress;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.ChallengeManager;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.MainActivity;

//import com.github.mikephil.charting.components.Description;

public class WeeklyProgressFragment extends DaggerFragment {

    private static String BAR_GRAPH_ACTIVITIES = "BAR_GRAPH_ACTIVITIES";
    private ArrayList<Integer> activities;
    private ArrayList<BarEntry> barEntries;
    private ArrayList<String> dates;
    @Inject
    ChallengeManager challengeManager;

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
            List<BarEntry> entries = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                entries.add(i, new BarEntry(i + 1, 0));
            }
            BarChart barChart = view.findViewById(R.id.bar_chart);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");

            try {
                Date date1 = simpleDateFormat.parse(simpleDateFormat.format(challengeManager.getRunningChallenge().getStartDatetime()));
                Date date2 = simpleDateFormat.parse(simpleDateFormat.format(challengeManager.getRunningChallenge().getEndDatetime()));
                Calendar mDate1 = Calendar.getInstance();
                Calendar mDate2 = Calendar.getInstance();
                mDate1.clear();
                mDate2.clear();

                mDate1.setTime(date1);
                mDate2.setTime(date2);

                dates = new ArrayList<>();
                dates = getList(mDate1, mDate2);

                barEntries = new ArrayList<>();

                for (int i = 0; i < activities.size(); i++) {
                    barEntries.add(new BarEntry(activities.get(i), i));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

            BarDataSet barDataSet = new BarDataSet(barEntries, "Number of Steps Logged");
            BarData barData = new BarData(dates, barDataSet);
            barChart.setData(barData);
            barChart.setBackgroundColor(getResources().getColor(R.color.white));
            barChart.setAutoScaleMinMaxEnabled(false);
            barChart.setTouchEnabled(false);
            barChart.setDescription("");
            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);

            xAxis.setDrawAxisLine(false);
            xAxis.setDrawGridLines(false);
            xAxis.setLabelsToSkip(0);
            Legend legend = barChart.getLegend();
            legend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
            barChart.invalidate();
            barChart.isShown();
        }

    }


    public ArrayList<String> getList(Calendar startDate, Calendar endDate) {
        ArrayList<String> list = new ArrayList<String>();
        while (startDate.compareTo(endDate) <= 0) {
            list.add(getDate(startDate));
            startDate.add(Calendar.DATE, 1);
        }
        return list;
    }

    public String getDate(Calendar cld) {
        String curDate = (cld.get(Calendar.MONTH) + 1) + "/"
                + cld.get(Calendar.DAY_OF_MONTH);
        try {
            Date date = new SimpleDateFormat("MM/dd").parse(curDate);
            curDate = new SimpleDateFormat("MM/dd").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return curDate;
    }
}
