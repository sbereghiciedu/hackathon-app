package org.hexed.hackathonapp.model.api.control;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ControlResponseModel {
    private String status;
    private String runningTime;
    private String seed;
    private int requestCount;
    private int maxActiveCalls;
    private long totalDispatches;
    private long targetDispatches;
    private double distance;
    private double penalty;
    private int httpRequests;
    private int emulatorVersion;
    private String signature;
    private String checksum;
    private Errors errors;

    // Constructors, getters, and setters

    public ControlResponseModel() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public int getMaxActiveCalls() {
        return maxActiveCalls;
    }

    public void setMaxActiveCalls(int maxActiveCalls) {
        this.maxActiveCalls = maxActiveCalls;
    }

    public long getTotalDispatches() {
        return totalDispatches;
    }

    public void setTotalDispatches(long totalDispatches) {
        this.totalDispatches = totalDispatches;
    }

    public long getTargetDispatches() {
        return targetDispatches;
    }

    public void setTargetDispatches(long targetDispatches) {
        this.targetDispatches = targetDispatches;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    public int getHttpRequests() {
        return httpRequests;
    }

    public void setHttpRequests(int httpRequests) {
        this.httpRequests = httpRequests;
    }

    public int getEmulatorVersion() {
        return emulatorVersion;
    }

    public void setEmulatorVersion(int emulatorVersion) {
        this.emulatorVersion = emulatorVersion;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public static class RunningTime {

        private long ticks;
        private int days;
        private int hours;
        private int milliseconds;
        private long microseconds;
        private long nanoseconds;
        private int minutes;
        private int seconds;
        private double totalDays;
        private double totalHours;
        private double totalMilliseconds;
        private double totalMicroseconds;
        private double totalNanoseconds;
        private double totalMinutes;
        private double totalSeconds;

        // Constructors, getters, and setters
        public RunningTime() {
        }
        // ... (getters and setters for RunningTime fields) ...
        public long getTicks() {
            return ticks;
        }

        public void setTicks(long ticks) {
            this.ticks = ticks;
        }

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public int getHours() {
            return hours;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }

        public int getMilliseconds() {
            return milliseconds;
        }

        public void setMilliseconds(int milliseconds) {
            this.milliseconds = milliseconds;
        }

        public long getMicroseconds() {
            return microseconds;
        }

        public void setMicroseconds(long microseconds) {
            this.microseconds = microseconds;
        }

        public long getNanoseconds() {
            return nanoseconds;
        }

        public void setNanoseconds(long nanoseconds) {
            this.nanoseconds = nanoseconds;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }

        public int getSeconds() {
            return seconds;
        }

        public void setSeconds(int seconds) {
            this.seconds = seconds;
        }

        public double getTotalDays() {
            return totalDays;
        }

        public void setTotalDays(double totalDays) {
            this.totalDays = totalDays;
        }

        public double getTotalHours() {
            return totalHours;
        }

        public void setTotalHours(double totalHours) {
            this.totalHours = totalHours;
        }

        public double getTotalMilliseconds() {
            return totalMilliseconds;
        }

        public void setTotalMilliseconds(double totalMilliseconds) {
            this.totalMilliseconds = totalMilliseconds;
        }

        public double getTotalMicroseconds() {
            return totalMicroseconds;
        }

        public void setTotalMicroseconds(double totalMicroseconds) {
            this.totalMicroseconds = totalMicroseconds;
        }

        public double getTotalNanoseconds() {
            return totalNanoseconds;
        }

        public void setTotalNanoseconds(double totalNanoseconds) {
            this.totalNanoseconds = totalNanoseconds;
        }

        public double getTotalMinutes() {
            return totalMinutes;
        }

        public void setTotalMinutes(double totalMinutes) {
            this.totalMinutes = totalMinutes;
        }

        public double getTotalSeconds() {
            return totalSeconds;
        }

        public void setTotalSeconds(double totalSeconds) {
            this.totalSeconds = totalSeconds;
        }
    }

    public static class Errors {

        private int missed;
        @JsonProperty("overDispatched") // needed because of the different naming convention.
        private int overDispatched;

        // Constructors, getters, and setters

        public Errors() {
        }

        public int getMissed() {
            return missed;
        }

        public void setMissed(int missed) {
            this.missed = missed;
        }

        public int getOverDispatched() {
            return overDispatched;
        }

        public void setOverDispatched(int overDispatched) {
            this.overDispatched = overDispatched;
        }
    }
}
