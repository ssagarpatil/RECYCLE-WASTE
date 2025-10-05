package com.ss.recyclewaste;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecipientsAdapter extends RecyclerView.Adapter<RecipientsAdapter.RecipientViewHolder> {

    private final List<Recipient> recipients;
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public RecipientsAdapter(List<Recipient> recipients, Context context) {
        this.recipients = recipients;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_recipient, parent, false);
        return new RecipientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipientViewHolder holder, int position) {
        Recipient recipient = recipients.get(position);
        holder.tvName.setText(recipient.name);
        holder.tvAddress.setText(recipient.address);
        holder.tvDescription.setText(recipient.description);

        // When Donate Now button is clicked, call the listener
        holder.btnDonate.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipients.size();
    }

    static class RecipientViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvDescription;
        Button btnDonate;

        public RecipientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            btnDonate = itemView.findViewById(R.id.btnDonate);
        }
    }
}
