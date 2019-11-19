package com.voilo;

public class Reminder {

    private String location;
    private String vehicle;
    private float lat;
    private float lon;

    public Reminder(String l, String v, Float la, Float lo) {
        this.location = l;
        this.vehicle = v;
        this.lat = la;
        this.lon = lo;
    }

    public String getLocation() {
        return location;
    }

    public String getVehicle() {
        return vehicle;
    }

    public float getLatitude() {
        return lat;
    }

    public float getLongitude() {
        return lon;
    }

}
