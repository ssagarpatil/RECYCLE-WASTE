package com.ss.recyclewaste;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainFeaturesAdapter extends RecyclerView.Adapter<MainFeaturesAdapter.ViewHolder> {
    private List<MainFeatureItem> mainFeatures;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(MainFeatureItem item);
    }

    public MainFeaturesAdapter(List<MainFeatureItem> mainFeatures) {
        this.mainFeatures = mainFeatures;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_feature, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainFeatureItem item = mainFeatures.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mainFeatures.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView iconImageView;
        TextView titleTextView;
        TextView descriptionTextView;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            iconImageView = itemView.findViewById(R.id.ivIcon);
            titleTextView = itemView.findViewById(R.id.tvTitle);
            descriptionTextView = itemView.findViewById(R.id.tvDescription);
        }

        void bind(MainFeatureItem item) {
            iconImageView.setImageResource(item.getIconRes());
            titleTextView.setText(item.getTitle());
            descriptionTextView.setText(item.getDescription());

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(item);
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
