package edu.neu.ccs.wellness.adcaregiverapp.presentation.gardenGazette;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.neu.ccs.wellness.adcaregiverapp.databinding.ItemGardenGazetteBinding;
import edu.neu.ccs.wellness.adcaregiverapp.domain.nursery.model.StoryPost;

public class GardenGazetteAdapter extends RecyclerView.Adapter<GardenGazetteAdapter.ViewHolder> {

    private List<StoryPost> result = new ArrayList<>();

    public GardenGazetteAdapter() {

    }

    public void setResult(List<StoryPost> result) {
        this.result.clear();
        this.result.addAll(result);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemGardenGazetteBinding binding = ItemGardenGazetteBinding.inflate(layoutInflater, parent, false);
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
        return result.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemGardenGazetteBinding binding;

        ViewHolder(ItemGardenGazetteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int pos) {
            String usernmae = result.get(pos).userName;
            String userMessage = result.get(pos).message;
            binding.username.setText(usernmae);
            binding.userMessage.setText(userMessage);
            binding.executePendingBindings();
        }

    }
}
