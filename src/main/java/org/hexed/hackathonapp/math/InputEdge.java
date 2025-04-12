package org.hexed.hackathonapp.math;

public class InputEdge {
    public int from;
    public int to;
    public int capacity;
    public int cost;

    public InputEdge(int from, int to, int capacity, int cost) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.cost = cost;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
