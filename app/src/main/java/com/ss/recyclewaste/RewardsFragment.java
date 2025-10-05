package com.ss.recyclewaste;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RewardsFragment extends Fragment {

    private TextView tvPoints;
    private RecyclerView recyclerView;
    private int totalPoints = 1000; // Mock total points
    private List<Reward> rewardList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rewards, container, false);

        tvPoints = view.findViewById(R.id.tvPoints);
        recyclerView = view.findViewById(R.id.recyclerView);

        tvPoints.setText("Total Points: " + totalPoints);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        rewardList = new ArrayList<>();
        loadRewards();

        RewardsAdapter adapter = new RewardsAdapter(rewardList, getContext(), totalPoints);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void loadRewards() {
        rewardList.add(new Reward("10% Off Eco-Products", "Get 10% off on selected items.", 100));
        rewardList.add(new Reward("Reusable Bag", "Redeem for a reusable shopping bag.", 500));
        rewardList.add(new Reward("Tree Plantation", "Donate points to plant trees.", 300));
        rewardList.add(new Reward("Eco Water Bottle", "Get a stainless steel water bottle.", 700));
    }
}

class Reward {
    String title;
    String description;
    int pointsRequired;

    Reward(String title, String description, int pointsRequired) {
        this.title = title;
        this.description = description;
        this.pointsRequired = pointsRequired;
    }
}

class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.RewardViewHolder> {

    private List<Reward> rewards;
    private int totalPoints;
    private android.content.Context context;

    RewardsAdapter(List<Reward> rewards, android.content.Context context, int totalPoints) {
        this.rewards = rewards;
        this.context = context;
        this.totalPoints = totalPoints;
    }

    @NonNull
    @Override
    public RewardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reward, parent, false);
        return new RewardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardViewHolder holder, int position) {
        Reward reward = rewards.get(position);
        holder.tvTitle.setText(reward.title);
        holder.tvDescription.setText(reward.description);
        holder.tvPoints.setText(reward.pointsRequired + " pts");

        holder.itemView.setOnClickListener(v -> {
            if (totalPoints >= reward.pointsRequired) {
                Toast.makeText(context, "Redeemed: " + reward.title, Toast.LENGTH_SHORT).show();
                // Here you would deduct points and notify backend
            } else {
                Toast.makeText(context, "Not enough points for " + reward.title, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return rewards.size();
    }

    static class RewardViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription, tvPoints;

        RewardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvRewardTitle);
            tvDescription = itemView.findViewById(R.id.tvRewardDescription);
            tvPoints = itemView.findViewById(R.id.tvRewardPoints);
        }
    }
}
