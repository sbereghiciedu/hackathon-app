package org.hexed.hackathonapp.model.api.medical;

public class DispatchModel {

    private String sourceCounty;
    private String sourceCity;
    private String targetCounty;
    private String targetCity;
    private int quantity;

    public DispatchModel() {
    }

    public DispatchModel(String sourceCounty, String sourceCity, String targetCounty, String targetCity, int quantity) {
        this.sourceCounty = sourceCounty;
        this.sourceCity = sourceCity;
        this.targetCounty = targetCounty;
        this.targetCity = targetCity;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getSourceCounty() {
        return sourceCounty;
    }

    public void setSourceCounty(String sourceCounty) {
        this.sourceCounty = sourceCounty;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    public String getTargetCounty() {
        return targetCounty;
    }

    public void setTargetCounty(String targetCounty) {
        this.targetCounty = targetCounty;
    }

    public String getTargetCity() {
        return targetCity;
    }

    public void setTargetCity(String targetCity) {
        this.targetCity = targetCity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Optional: Provide a meaningful toString implementation
    @Override
    public String toString() {
        return "DispatchModel{" +
                "sourceCounty='" + sourceCounty + '\'' +
                ", sourceCity='" + sourceCity + '\'' +
                ", targetCounty='" + targetCounty + '\'' +
                ", targetCity='" + targetCity + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}