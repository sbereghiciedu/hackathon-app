package org.hexed.hackathonapp.model.api.control;

public class ResetParamsModel {

    private String seed;
    private int targetDispatches;
    private int maxActiveCalls;

    public ResetParamsModel(String seed, int targetDispatches, int maxActiveCalls) {
        this.seed = seed;
        this.targetDispatches = targetDispatches;
        this.maxActiveCalls = maxActiveCalls;
    }

    // Getters and Setters
    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public int getTargetDispatches() {
        return targetDispatches;
    }

    public void setTargetDispatches(int targetDispatches) {
        this.targetDispatches = targetDispatches;
    }

    public int getMaxActiveCalls() {
        return maxActiveCalls;
    }

    public void setMaxActiveCalls(int maxActiveCalls) {
        this.maxActiveCalls = maxActiveCalls;
    }

    // Optional: Provide a meaningful toString method
    @Override
    public String toString() {
        return "ResetParamsModel{" +
                "seed='" + seed + '\'' +
                ", targetDispatches=" + targetDispatches +
                ", maxActiveCalls=" + maxActiveCalls +
                '}';
    }
}