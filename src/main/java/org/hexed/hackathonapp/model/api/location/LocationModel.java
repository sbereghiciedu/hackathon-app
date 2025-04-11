package org.hexed.hackathonapp.model.api.location;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationModel {
    @JsonProperty("name")
    private String name;
    @JsonProperty("county")
    private String county;
    @JsonProperty("lat")
    private double latitude;
    @JsonProperty("long")
    private double longitude;

    // Constructors, getters, setters, and toString()
    public LocationModel() {
    } // Required for Jackson

    public LocationModel(String name, String county, double latitude, double longitude) {
        this.name = name;
        this.county = county;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", county='" + county + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}