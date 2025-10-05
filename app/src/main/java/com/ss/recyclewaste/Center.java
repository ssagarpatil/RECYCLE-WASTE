package com.ss.recyclewaste;

public class Center {
    private String name;
    private String address;
    private String workingHours;
    private String acceptedWasteTypes;

    public Center(String name, String address, String workingHours, String acceptedWasteTypes) {
        this.name = name;
        this.address = address;
        this.workingHours = workingHours;
        this.acceptedWasteTypes = acceptedWasteTypes;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public String getAcceptedWasteTypes() {
        return acceptedWasteTypes;
    }
}
