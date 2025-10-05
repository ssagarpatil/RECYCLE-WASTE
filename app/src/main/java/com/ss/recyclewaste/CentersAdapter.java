package com.ss.recyclewaste;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CentersAdapter extends RecyclerView.Adapter<CentersAdapter.CenterViewHolder> {

    private List<Center> centers;
    private Context context;
    private String selectedWasteType;

    public CentersAdapter(List<Center> centers, Context context, String selectedWasteType) {
        this.centers = centers;
        this.context = context;
        this.selectedWasteType = selectedWasteType;
    }

    @NonNull
    @Override
    public CenterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_center, parent, false);
        return new CenterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CenterViewHolder holder, int position) {
        Center center = centers.get(position);
        holder.name.setText(center.getName());
        holder.address.setText(center.getAddress());
        holder.hours.setText(center.getWorkingHours());
        holder.acceptedTypes.setText(center.getAcceptedWasteTypes());

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("selected_waste", selectedWasteType);
            bundle.putString("center_name", center.getName());
            bundle.putString("center_address", center.getAddress());
            // Add more data if needed

            PickupFragment pickupFragment = new PickupFragment();
            pickupFragment.setArguments(bundle);

            if (context instanceof MainActivity) {
                ((MainActivity)context).getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .replace(R.id.main_container, pickupFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return centers.size();
    }

    static class CenterViewHolder extends RecyclerView.ViewHolder {
        TextView name, address, hours, acceptedTypes;

        public CenterViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.center_name);
            address = itemView.findViewById(R.id.center_address);
            hours = itemView.findViewById(R.id.center_hours);
            acceptedTypes = itemView.findViewById(R.id.center_waste_types);
        }
    }
}
