package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.model.api.calls.RequestDetail;
import org.hexed.hackathonapp.model.api.calls.RequestModel;
import org.hexed.hackathonapp.model.api.calls.RequestType;
import org.hexed.hackathonapp.model.api.interventioncenter.InterventionCenterModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State {

    private final Map<RequestType, List<Request>> requests;
    private final Map<RequestType, List<InterventionCenterModel>> interventionCenters;

    public State() {
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
}
