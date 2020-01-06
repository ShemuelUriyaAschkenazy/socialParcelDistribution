package com.example.socialparceldistribution.Entities;

public class UserLocation {
    private Double lat;
    private Double lang;

    public double getLat() {
        return lat;
    }

    public double getLang() {
        return lang;
    }

    public UserLocation(double lat, double lang) {
        this.lat = lat;
        this.lang = lang;
    }

    public UserLocation() {
    }
}
