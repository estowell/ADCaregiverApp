package edu.neu.ccs.wellness.adcaregiverapp.presentation.garden;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.NumberUtils;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.ItemMyGardenBinding;

public class GardenAdapter extends RecyclerView.Adapter<GardenAdapter.ViewHolder> {

    private boolean selectionEnabled;
    private Context context;

    private List<UserGardenModel> data = new ArrayList<>();

    public GardenAdapter(Context context, boolean selectionEnabled) {
        this.selectionEnabled = selectionEnabled;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMyGardenBinding binding = ItemMyGardenBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UserGardenModel model = data.get(position);
        holder.binding.flowerImage.setBackgroundColor(holder.backgroundColor());
        if (model.getFlowerDrawableName() != null && !selectionEnabled) {

            Resources resources = context.getResources();
            final int resourceId = resources.getIdentifier(model.getFlowerDrawableName(), "drawable",
                    context.getPackageName());
            holder.binding.flowerImage.setImageResource(resourceId);

        } else if (model.getFlowerDrawableName() != null && selectionEnabled) {
            Resources resources = context.getResources();
            final int resourceId = resources.getIdentifier(model.getFlowerDrawableName(), "drawable",
                    context.getPackageName());
            holder.binding.flowerImage.setImageResource(resourceId);
            holder.binding.flowerImage.setAlpha(.4f);
        }

        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<UserGardenModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemMyGardenBinding binding;
        private int colorCount = 0;
        private int[] array;

        public ViewHolder(ItemMyGardenBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            array = itemView.getContext().getResources().getIntArray(R.array.gridColors);
        }

        int backgroundColor() {

            if (colorCount == array.length - 1) {
                colorCount = 0;
            }
            int randomColor = array[NumberUtils.getRandomNumber(colorCount, array.length - 1)];
            colorCount++;
            return randomColor;
        }
    }


}
