package edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.viewPagerFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.UserManager;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.LogExerciseModel;

import static edu.neu.ccs.wellness.adcaregiverapp.common.utils.Constants.EXERCISE_LOG;

public class ExerciseLogFragment extends DaggerFragment {


    private BarChart chart;
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
        chart = view.findViewById(R.id.bar_chart);

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

                updateChartData(logs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void updateChartData(List<LogExerciseModel> exerciseModels) {
        Map<Float, Float> dailyLogs = new TreeMap<>();

        for (float i = 1; i <= 7; i++) {
            dailyLogs.put(i, 0f);
        }
        Calendar curr = Calendar.getInstance();
        Calendar model = Calendar.getInstance();
        List<BarEntry> entries = new ArrayList<>();

        MapDateToWeek(exerciseModels, dailyLogs, curr, model);

        for (Map.Entry entry : dailyLogs.entrySet()) {
            entries.add(new BarEntry((float) entry.getKey(), (float) entry.getValue()));
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

    private void MapDateToWeek(List<LogExerciseModel> exerciseModels, Map<Float, Float> dailyLogs, Calendar curr, Calendar model) {
        for (LogExerciseModel exerciseModel : exerciseModels) {
            model.setTimeInMillis(exerciseModel.getTimeLong());
            if (model.get(Calendar.DATE) == (curr.get(Calendar.DATE) - 6)) {
                updateMap(dailyLogs, 1f);
            } else if (model.get(Calendar.DATE) == (curr.get(Calendar.DATE) - 5)) {
                updateMap(dailyLogs, 2f);
            } else if (model.get(Calendar.DATE) == (curr.get(Calendar.DATE) - 4)) {
                updateMap(dailyLogs, 3f);
            } else if (model.get(Calendar.DATE) == (curr.get(Calendar.DATE) - 3)) {
                updateMap(dailyLogs, 4f);
            } else if (model.get(Calendar.DATE) == (curr.get(Calendar.DATE) - 2)) {
                updateMap(dailyLogs, 5f);
            } else if (model.get(Calendar.DATE) == (curr.get(Calendar.DATE) - 1)) {
                updateMap(dailyLogs, 6f);
            } else if (model.get(Calendar.DATE) == (curr.get(Calendar.DATE))) {
                updateMap(dailyLogs, 7f);
            }
        }
    }

    private void updateMap(Map<Float, Float> dailyLogs, float day) {
        if (dailyLogs.containsKey(day)) {
            dailyLogs.put(day, dailyLogs.get(day) + 1);
        } else {
            dailyLogs.put(day, 1f);
        }
    }

}
