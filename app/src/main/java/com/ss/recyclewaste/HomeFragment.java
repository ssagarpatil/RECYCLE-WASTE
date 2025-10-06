package com.ss.recyclewaste;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    // Views
    private RecyclerView recyclerViewQuickActions;
    private RecyclerView recyclerViewMainFeatures;
    private FloatingActionButton fabScanQR;

    // Adapters
    private QuickActionAdapter quickActionAdapter;
    private MainFeaturesAdapter mainFeaturesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);
        setupRecyclerViews();
        setupQuickActions();
        setupMainFeatures();
        setupClickListeners();

        return view;
    }

    private void initViews(View view) {
        recyclerViewQuickActions = view.findViewById(R.id.recyclerViewQuickActions);
        recyclerViewMainFeatures = view.findViewById(R.id.recyclerViewMainFeatures);
        fabScanQR = view.findViewById(R.id.fabScanQR);
    }

    private void setupRecyclerViews() {
        // Setup Quick Actions RecyclerView (Horizontal)
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        recyclerViewQuickActions.setLayoutManager(horizontalLayoutManager);
        recyclerViewQuickActions.setNestedScrollingEnabled(false);

        // Setup Main Features RecyclerView (Vertical Grid)
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerViewMainFeatures.setLayoutManager(gridLayoutManager);
        recyclerViewMainFeatures.setNestedScrollingEnabled(false);
    }

    private void setupQuickActions() {
        // Create Quick Actions Data
        List<QuickActionItem> quickActions = Arrays.asList(
//                new QuickActionItem("Scan QR", R.drawable.ic_qr_scan, R.color.primary_green),
                new QuickActionItem("Sell Waste", R.drawable.img_1, R.color.accent_orange),
//                new QuickActionItem("Schedule Pickup", R.drawable.img_2, R.color.accent_blue),
                new QuickActionItem("View Rewards", R.drawable.ic_rewards, R.color.accent_purple)
//                new QuickActionItem("Track Order", R.drawable.ic_rewards, R.color.accent_purple)
        );

        // Setup Adapter
        quickActionAdapter = new QuickActionAdapter(quickActions);
        quickActionAdapter.setOnItemClickListener(this::handleQuickActionClick);
        recyclerViewQuickActions.setAdapter(quickActionAdapter);
    }

    private void setupMainFeatures() {
        // Create Main Features Data
        List<MainFeatureItem> mainFeatures = Arrays.asList(
                new MainFeatureItem(
                        "Recycle Waste",
                        "Convert waste to rewards",
                        R.drawable.ic_recycle,
                        MainFeatureItem.FeatureType.RECYCLE_WASTE
                ),
                new MainFeatureItem(
                        "Donate Food",
                        "Share surplus food with community",
                        R.drawable.ic_food,
                        MainFeatureItem.FeatureType.DONATE_FOOD
                ),
                new MainFeatureItem(
                        "Nearby Centers",
                        "Find waste collection points",
                        R.drawable.ic_location,
                        MainFeatureItem.FeatureType.NEARBY_CENTERS
                ),
                new MainFeatureItem(
                        "My Rewards",
                        "Check earned points and rewards",
                        R.drawable.ic_rewards,
                        MainFeatureItem.FeatureType.MY_REWARDS
                ),
                new MainFeatureItem(
                        "Transaction History",
                        "View your waste disposal history",
                        R.drawable.img_6,
                        MainFeatureItem.FeatureType.TRANSACTION_HISTORY
                ),
                new MainFeatureItem(
                        "Settings",
                        "Manage account and preferences",
                        R.drawable.img_7,
                        MainFeatureItem.FeatureType.SETTINGS
                ),
                new MainFeatureItem(
                        "Eco Tips",
                        "Learn sustainable practices",
                        R.drawable.img_8,
                        MainFeatureItem.FeatureType.TIPS
                )
        );

        // Setup Adapter
        mainFeaturesAdapter = new MainFeaturesAdapter(mainFeatures);
        mainFeaturesAdapter.setOnItemClickListener(this::handleMainFeatureClick);
        recyclerViewMainFeatures.setAdapter(mainFeaturesAdapter);
    }

    private void setupClickListeners() {
        // Floating Action Button Click
        fabScanQR.setOnClickListener(v -> {
            // Handle QR Scan
            Toast.makeText(getContext(), "Opening QR Scanner...", Toast.LENGTH_SHORT).show();
            // You can add intent to open QR scanner activity here
            // Intent intent = new Intent(getActivity(), QRScannerActivity.class);
            // startActivity(intent);
        });
    }

    private void handleQuickActionClick(QuickActionItem item) {
        switch (item.getTitle()) {
            case "Scan QR":
                Toast.makeText(getContext(), "Opening QR Scanner", Toast.LENGTH_SHORT).show();
                break;
            case "Sell Waste":
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).loadFragment(new WasteTypeFragment());
                }
                break;
            case "Schedule Pickup":
                Toast.makeText(getContext(), "Scheduling Pickup", Toast.LENGTH_SHORT).show();
                break;
            case "View Rewards":
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).highlightMenuItem(R.id.nav_rewards);
                    ((MainActivity) getActivity()).loadFragment(new RewardsFragment());
                }
                break;
            case "Track Order":
                Toast.makeText(getContext(), "Tracking Order", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void handleMainFeatureClick(MainFeatureItem item) {
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();

            switch (item.getFeatureType()) {
                case RECYCLE_WASTE:
                    mainActivity.highlightMenuItem(R.id.nav_waste);
                    mainActivity.loadFragment(new WasteTypeFragment());
                    break;

                case DONATE_FOOD:
                    mainActivity.highlightMenuItem(R.id.nav_food);
                    mainActivity.loadFragment(new FoodDonationFragment());
                    break;

                case NEARBY_CENTERS:
                    mainActivity.highlightMenuItem(R.id.nav_centers);
                    mainActivity.loadFragment(new CentersFragment());
                    break;

                case MY_REWARDS:
                    mainActivity.highlightMenuItem(R.id.nav_rewards);
                    mainActivity.loadFragment(new RewardsFragment());
                    break;

                case TRANSACTION_HISTORY:
                    mainActivity.loadFragment(new TransactionHistoryFragment());
                    break;

                case SETTINGS:
                    mainActivity.loadFragment(new SettingsFragment());
                    break;

                case TIPS:
                    mainActivity.loadFragment(new TipsFragment());
                    break;
            }
        }
    }
}
