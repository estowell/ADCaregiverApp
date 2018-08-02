package edu.neu.ccs.wellness.adcaregiverapp.presentation.exercises.tutorialFragments;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.neu.ccs.wellness.adcaregiverapp.databinding.ItemTutorialListBinding;
import edu.neu.ccs.wellness.adcaregiverapp.domain.exercise.usecase.model.Exercise;

public class TutorialListAdapter extends RecyclerView.Adapter<TutorialListAdapter.ViewHolder> {

    private List<Exercise> data = new ArrayList<>();
    private TutorialListFragment.TutorialListCallBack callback;

    public void setData(List<Exercise> data) {
        this.data.clear();
        this.data = data;
        notifyDataSetChanged();
    }

    public TutorialListAdapter(TutorialListFragment.TutorialListCallBack callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemTutorialListBinding binding = ItemTutorialListBinding.inflate(layoutInflater, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        //TODO: Fix ME
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemTutorialListBinding binding;

        public ViewHolder(ItemTutorialListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            binding.headText.setText(data.get(position).getHeadText());
            binding.exerciseName.setText(data.get(position).getExerciseName());
            binding.exerciseSteps.setText(formatSteps(data.get(position).getSteps()));
            binding.logExercise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onLogButtonClicked();
                }
            });
            binding.executePendingBindings();
        }

    }

    private String formatSteps(List<String> steps) {

        if (steps.size() == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < steps.size(); i++) {
            sb.append(String.valueOf(i + 1)).append(": ").append(steps.get(i)).append("\n").append("\n");
        }

        return sb.toString();

    }
}
