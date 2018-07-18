package edu.neu.ccs.wellness.adcaregiverapp.presentation.communityGarden;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.neu.ccs.wellness.adcaregiverapp.R;
import edu.neu.ccs.wellness.adcaregiverapp.common.utils.NumberUtils;
import edu.neu.ccs.wellness.adcaregiverapp.network.services.model.Member;
import edu.neu.ccs.wellness.adcaregiverapp.presentation.gardenGazette.GardenGazetteFragment;


/**
 * Created by amritanshtripathi on 6/10/18.
 */

public class CommunityGardenAdapter extends RecyclerView.Adapter<CommunityGardenAdapter.ViewHolder> {


    private List<Member> data;


    public CommunityGardenAdapter() {
        data = new ArrayList<>(10);
        data.add(0, new Member("Garden Gazette"));
    }

    @NonNull
    @Override
    public CommunityGardenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_community_garden, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (position == 0) {
            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToGardenGazette(holder.itemView);
                }
            });
        }
        String name = data.get(position).getName();
        if (name == null) holder.textView.setText("");
        else holder.textView.setText(name);
        holder.itemView.setBackgroundColor(holder.backgroundColor());
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }

    public void setData(List<Member> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    private void navigateToGardenGazette(View itemView) {
        FragmentManager fragmentManager = ((Activity) itemView.getContext()).getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragment_container, GardenGazetteFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        View itemView;
        LinearLayout container;
        private int colorCount = 0;
        private int[] array;

        ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            textView = itemView.findViewById(R.id.community_TextView);
            array = itemView.getContext().getResources().getIntArray(R.array.gridColors);
            container = itemView.findViewById(R.id.item_container);

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
