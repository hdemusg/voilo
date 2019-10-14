package com.voilo;

public class Reminder {

    private String location;
    private String vehicle;

    public Reminder(String l, String v) {
        this.location = l;
        this.vehicle = v;
    }

    public String getLocation() {
        return location;
    }

    public String getVehicle() {
        return vehicle;
    }
}
