package com.ss.recyclewaste;

public class MainFeatureItem {
    private String title;
    private String description;
    private int iconRes;
    private FeatureType featureType;

    public enum FeatureType {
        RECYCLE_WASTE,
        DONATE_FOOD,
        NEARBY_CENTERS,
        MY_REWARDS,
        TRANSACTION_HISTORY,
        SETTINGS,
        TIPS
    }

    public MainFeatureItem(String title, String description, int iconRes, FeatureType featureType) {
        this.title = title;
        this.description = description;
        this.iconRes = iconRes;
        this.featureType = featureType;
    }

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getIconRes() { return iconRes; }
    public FeatureType getFeatureType() { return featureType; }
}
