package com.ss.recyclewaste;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class QuickActionAdapter extends RecyclerView.Adapter<QuickActionAdapter.ViewHolder> {
    private List<QuickActionItem> quickActions;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(QuickActionItem item);
    }

    public QuickActionAdapter(List<QuickActionItem> quickActions) {
        this.quickActions = quickActions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_quick_action, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuickActionItem item = quickActions.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return quickActions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView iconImageView;
        TextView titleTextView;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            iconImageView = itemView.findViewById(R.id.ivIcon);
            titleTextView = itemView.findViewById(R.id.tvTitle);
        }

        void bind(QuickActionItem item) {
            iconImageView.setImageResource(item.getIconRes());
            titleTextView.setText(item.getTitle());
            cardView.setCardBackgroundColor(
                    ContextCompat.getColor(itemView.getContext(), item.getColorRes())
            );

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
