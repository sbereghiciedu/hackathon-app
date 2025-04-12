package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.model.api.calls.RequestModel;
import org.hexed.hackathonapp.model.api.calls.RequestType;
import org.hexed.hackathonapp.model.api.interventioncenter.InterventionCenterModel;
import org.hexed.hackathonapp.model.api.location.LocationModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class State {

    private final Map<RequestType, List<Request>> requests;
    private final Map<RequestType, List<InterventionCenterModel>> interventionCenters;
    private final int[][] distances;
    private final List<LocationModel> locations;

    public int[][] getDistances() {
        return distances;
    }

    public List<LocationModel> getLocations() {
        return locations;
    }

    public State(List<LocationModel> locations) {
        distances = computeDistances(locations);
        this.locations = locations;
        requests = new HashMap<>();
        interventionCenters  = new HashMap<>();
        for (RequestType type : RequestType.values()) {
            interventionCenters.put(type, new ArrayList<>());
            requests.put(type, new ArrayList<>());
        }
    }

    public void addRequest(RequestModel request) {
        for (RequestType type : RequestType.values()) {
            int q = request.getRequest(type).getQuantity();
            if (q > 0) {
                Request req = new Request(request.getCounty(), request.getCity(), request.getLatitude(), request.getLongitude(), q);
                getRequests().get(type).add(req);
            }
        }
    }

    public Map<RequestType, List<Request>> getRequests() {
        return requests;
    }

    public Map<RequestType, List<InterventionCenterModel>> getInterventionCenters() {
        return interventionCenters;
    }

    public static class Request {
        private final String county;
        private final String city;
        private final double lat;
        private final double lon;
        private int q;

        public Request(String county, String city, double lat, double lon, int q) {
            this.county = county;
            this.city = city;
            this.lat = lat;
            this.lon = lon;
            this.q = q;
        }

        public String getCounty() {
            return county;
        }

        public String getCity() {
            return city;
        }

        public double getLat() {
            return lat;
        }

        public double getLon() {
            return lon;
        }

        public int getQ() {
            return q;
        }

        public void setQ(int q) {
            this.q = q;
        }
    }

    public int[][] computeDistances(List<LocationModel> locations) {
        int size = locations.size();
        int[][] distanceMatrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    distanceMatrix[i][j] = cartesianDistance(
                            locations.get(i).getLatitude(),
                            locations.get(i).getLongitude(),
                            locations.get(j).getLatitude(),
                            locations.get(j).getLongitude()
                    );
                } else {
                    distanceMatrix[i][j] = 0; // Distance to self is 0
                }
            }
        }

        return distanceMatrix;
    }

    public static int cartesianDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        // Calculate the Cartesian (Euclidean) distance
        return (int) (sqrt(pow(latitude2 - latitude1, 2) + pow(longitude2 - longitude1, 2))* 10000) ;
    }
}
