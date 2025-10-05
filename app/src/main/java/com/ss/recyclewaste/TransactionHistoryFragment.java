package com.ss.recyclewaste;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView emptyText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction_history, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        emptyText = view.findViewById(R.id.emptyText);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        // Mock transaction data
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("Recycle Waste", "Plastic Bottle", "2025-10-01", "Completed"));
        transactions.add(new Transaction("Food Donation", "Meals for All NGO", "2025-10-02", "Completed"));
        transactions.add(new Transaction("Recycle Waste", "Metal Can", "2025-10-04", "Pending"));
        transactions.add(new Transaction("Food Donation", "Children's Shelter", "2025-10-05", "Scheduled"));

        // Show empty state if no transactions
        if (transactions.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
            TransactionAdapter adapter = new TransactionAdapter(transactions);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }

    // Model class
    static class Transaction {
        String type, itemOrRecipient, date, status;

        Transaction(String type, String itemOrRecipient, String date, String status) {
            this.type = type;
            this.itemOrRecipient = itemOrRecipient;
            this.date = date;
            this.status = status;
        }
    }

    // RecyclerView Adapter
    static class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
        private final List<Transaction> transactions;

        TransactionAdapter(List<Transaction> transactions) {
            this.transactions = transactions;
        }

        @NonNull
        @Override
        public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
            return new TransactionViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
            Transaction transaction = transactions.get(position);
            holder.tvType.setText(transaction.type);
            holder.tvItem.setText(transaction.itemOrRecipient);
            holder.tvDate.setText(transaction.date);

            // Set status color
            if (transaction.status.equals("Completed")) {
                holder.tvStatus.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_green_dark));
            } else if (transaction.status.equals("Scheduled")) {
                holder.tvStatus.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_blue_dark));
            } else {
                holder.tvStatus.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_orange_dark));
            }
            holder.tvStatus.setText(transaction.status);
        }

        @Override
        public int getItemCount() {
            return transactions.size();
        }

        static class TransactionViewHolder extends RecyclerView.ViewHolder {
            TextView tvType, tvItem, tvDate, tvStatus;

            TransactionViewHolder(@NonNull View itemView) {
                super(itemView);
                tvType = itemView.findViewById(R.id.tvType);
                tvItem = itemView.findViewById(R.id.tvItem);
                tvDate = itemView.findViewById(R.id.tvDate);
                tvStatus = itemView.findViewById(R.id.tvStatus);
            }
        }
    }
}
