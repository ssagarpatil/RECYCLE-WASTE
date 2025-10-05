package com.ss.recyclewaste;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Recycle, Food, Centers, Rewards Cards
        CardView cardRecycle = view.findViewById(R.id.card_recycle);
        CardView cardFood = view.findViewById(R.id.card_food);
        CardView cardCenters = view.findViewById(R.id.card_centers);
        CardView cardRewards = view.findViewById(R.id.card_rewards);

        // New Cards: Transaction History, Settings, Tips
        CardView cardTransactionHistory = view.findViewById(R.id.card_transaction_history);
        CardView cardSettings = view.findViewById(R.id.card_settings);
        CardView cardTips = view.findViewById(R.id.card_tips);

        // Recycle Waste
        cardRecycle.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).highlightMenuItem(R.id.nav_waste);
                ((MainActivity) getActivity()).loadFragment(new WasteTypeFragment());
            }
        });

        // Donate Food
        cardFood.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).highlightMenuItem(R.id.nav_food);
                ((MainActivity) getActivity()).loadFragment(new FoodDonationFragment());
            }
        });

        // Nearby Centers
        cardCenters.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).highlightMenuItem(R.id.nav_centers);
                ((MainActivity) getActivity()).loadFragment(new CentersFragment());
            }
        });

        // Rewards
        cardRewards.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).highlightMenuItem(R.id.nav_rewards);
                ((MainActivity) getActivity()).loadFragment(new RewardsFragment());
            }
        });

        // Transaction History
        cardTransactionHistory.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
//                ((MainActivity) getActivity()).highlightMenuItem(R.id.nav_transaction_history);
                ((MainActivity) getActivity()).loadFragment(new TransactionHistoryFragment());
            }
        });

        // Settings
        cardSettings.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
//                ((MainActivity) getActivity()).highlightMenuItem(R.id.nav_settings);
                ((MainActivity) getActivity()).loadFragment(new SettingsFragment());
            }
        });

        // Tips
        cardTips.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
//                ((MainActivity) getActivity()).highlightMenuItem(R.id.nav_tips);
                ((MainActivity) getActivity()).loadFragment(new TipsFragment());
            }
        });

        return view;
    }
}
