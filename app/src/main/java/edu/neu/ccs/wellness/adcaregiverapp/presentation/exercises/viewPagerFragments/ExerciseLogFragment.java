package edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.viewPagerFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.UserManager;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.LogExerciseModel;

import static edu.neu.ccs.wellness.adcaregiverapp.common.utils.Constants.EXERCISE_LOG;

public class ExerciseLogFragment extends DaggerFragment {


    private BarChart barChart;
    private ArrayList<String> dates;
    private ArrayList barEntries;
    @Inject
    UserManager userManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_exercise, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        barChart = view.findViewById(R.id.bar_chart);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query reference = database.getReference().child(EXERCISE_LOG).orderByKey();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<LogExerciseModel> logs = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    LogExerciseModel log = snapshot.getValue(LogExerciseModel.class);
                    if (Objects.requireNonNull(log).getUserId() == Objects.requireNonNull(userManager.getUser()).getUserId()) {
                        logs.add(log);
                    }
                }
                createBarGraph(logs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void createBarGraph(List<LogExerciseModel> logs) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");

        try {
            Date date1 = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Date date2 = simpleDateFormat.parse(simpleDateFormat.format(new Date()));

            Calendar mDate1 = Calendar.getInstance();
            Calendar mDate2 = Calendar.getInstance();
            mDate1.clear();
            mDate2.clear();

            mDate1.setTime(date1);
            mDate2.setTime(date2);
            mDate2.add(Calendar.DATE, -6);

            dates = new ArrayList<>();
            dates = getList(mDate2, mDate1);

            barEntries = new ArrayList<>();

            for (int j = 0; j < dates.size(); j++) {
                String date = dates.get(j);
                Date dateObject = simpleDateFormat.parse(date);
                Calendar curr = Calendar.getInstance();

                curr.setTimeInMillis(dateObject.getTime());
                int dataPoints = 0;
                dataPoints = getDataPoints(logs, curr, dataPoints);
                barEntries.add(new BarEntry(dataPoints, j));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "Number of Exercises Logged");
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

    private int getDataPoints(List<LogExerciseModel> logs, Calendar curr, int dataPoints) {
        for (int i = 0; i < logs.size(); i++) {
            if (logs.get(i).getUserId() == userManager.getUser().getUserId()) {
                Calendar model = Calendar.getInstance();
                model.setTimeInMillis(logs.get(i).getTimeLong());
                if (model.get(Calendar.DATE) == curr.get(Calendar.DATE)) {
                    dataPoints++;
                }
            }
        }
        return dataPoints;
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
