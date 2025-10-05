package com.ss.recyclewaste;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WasteTypeAdapter extends RecyclerView.Adapter<WasteTypeAdapter.WasteTypeViewHolder> {

    private List<WasteTypeItem> wasteTypeList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(WasteTypeItem item);
    }

    public WasteTypeAdapter(List<WasteTypeItem> wasteTypeList, Context context, OnItemClickListener listener) {
        this.wasteTypeList = wasteTypeList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WasteTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_waste_type, parent, false);
        return new WasteTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WasteTypeViewHolder holder, int position) {
        WasteTypeItem item = wasteTypeList.get(position);
        holder.icon.setImageResource(item.getIconResId());
        holder.name.setText(item.getName());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return wasteTypeList.size();
    }

    static class WasteTypeViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;

        public WasteTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.waste_icon);
            name = itemView.findViewById(R.id.waste_name);
        }
    }
}
