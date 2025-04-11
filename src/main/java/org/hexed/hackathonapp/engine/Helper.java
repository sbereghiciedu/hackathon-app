package org.hexed.hackathonapp.engine;

public class Helper {

    public static double distance(double lat0, double lon0, double lat1, double lon1) {
        double dLat = lat0 - lat1;
        double dLon = lon0 - lon1;

        return Math.sqrt(dLat*dLat + dLon*dLon);
    }
}
