package com.ss.recyclewaste;

public class WasteTypeItem {
    private String name;
    private int iconResId;

    public WasteTypeItem(String name, int iconResId) {
        this.name = name;
        this.iconResId = iconResId;
    }

    public String getName() {
        return name;
    }

    public int getIconResId() {
        return iconResId;
    }
}
