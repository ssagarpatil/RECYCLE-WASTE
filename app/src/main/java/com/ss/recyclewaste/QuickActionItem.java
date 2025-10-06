package com.ss.recyclewaste;

public class QuickActionItem {
    private String title;
    private int iconRes;
    private int colorRes;

    public QuickActionItem(String title, int iconRes, int colorRes) {
        this.title = title;
        this.iconRes = iconRes;
        this.colorRes = colorRes;
    }

    // Getters
    public String getTitle() { return title; }
    public int getIconRes() { return iconRes; }
    public int getColorRes() { return colorRes; }
}
