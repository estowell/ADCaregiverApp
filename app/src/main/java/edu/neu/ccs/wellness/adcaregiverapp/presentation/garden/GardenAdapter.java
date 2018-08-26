package edu.neu.ccs.wellness.adcaregiverapp.presentation.garden;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.DrawableUntils;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.NumberUtils;
import edu.neu.ccs.wellness.adcaregiverapp.databinding.ItemMyGardenBinding;

public class GardenAdapter extends RecyclerView.Adapter<GardenAdapter.ViewHolder> {

    private boolean selectionEnabled = false;
    private Context context;
    private int numberOfSelections;
    private int flowerResource;
    private GardenFragment.GardenFragmentCallBack callBack;
    private Set<Integer> selectedPositionSet = new HashSet<>();

    private UserGardenModel[] data;

    public GardenAdapter(Context context) {
        this.context = context;
    }

    public GardenAdapter(Context context, boolean selectionEnabled, int numberOfSelections, int flowerResource, GardenFragment.GardenFragmentCallBack callBack) {
        this.selectionEnabled = selectionEnabled;
        this.context = context;
        this.numberOfSelections = numberOfSelections;
        this.flowerResource = flowerResource;
        this.callBack = callBack;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMyGardenBinding binding = ItemMyGardenBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder holder = new ViewHolder(binding);
        //TODO: Fix ME
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        UserGardenModel model = data[position];
        holder.binding.flowerImage.setBackgroundColor(holder.backgroundColor());

        if (model != null && model.getFlowerDrawableName() != null && !selectionEnabled) {
            String name = model.getFlowerDrawableName();
            int stage = model.getStage();
            holder.binding.flowerImage.setImageResource(DrawableUntils.getDrawableIdByNameAndStage(context, name, stage));

        } else if (model != null && model.getFlowerDrawableName() != null && selectionEnabled) {
            String name = model.getFlowerDrawableName();
            int stage = model.getStage();
            holder.binding.flowerImage.setImageResource(DrawableUntils.getDrawableIdByNameAndStage(context, name, stage));
            holder.binding.flowerImage.setAlpha(.4f);
        } else {
            if (selectionEnabled) {
                holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectedPositionSet.contains(position)) {
                            holder.binding.flowerImage.setImageResource(android.R.color.transparent);
                            selectedPositionSet.remove(position);
                            callBack.onBlockSelected(selectedPositionSet);
                        } else if (selectedPositionSet.size() < numberOfSelections) {
                            holder.binding.flowerImage.setImageResource(flowerResource);
                            holder.binding.flowerImage.setVisibility(View.VISIBLE);
                            selectedPositionSet.add(position);
                            callBack.onBlockSelected(selectedPositionSet);
                        } else {
                            Toast.makeText(context, "Maximum number of selections reached", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        }

        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.length;
    }

    public void setData(UserGardenModel[] data) {
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
