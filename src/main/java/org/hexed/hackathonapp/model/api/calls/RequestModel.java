package org.hexed.hackathonapp.model.api.calls;

import java.util.List;

public class RequestModel {
    private String city;
    private String county;
    private double latitude;
    private double longitude;
    private List<RequestDetail> requests;


    // Getters and Setters

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public List<RequestDetail> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestDetail> requests) {
        this.requests = requests;
    }

    public RequestDetail getRequest(RequestType type) {
        RequestDetail result = null;
        for (RequestDetail request : requests) {
            if (request.getType().equalsIgnoreCase(type.getKey())) {
                result = request;
                break;
            }
        }
        return result;
    }


}
