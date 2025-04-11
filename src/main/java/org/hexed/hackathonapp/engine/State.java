package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.model.api.calls.CallsNextResponseModel;
import org.hexed.hackathonapp.model.api.medical.LocationWithQuantityModel;

import java.util.ArrayList;
import java.util.List;

public class State {

    private List<CallsNextResponseModel> requests;
    private List<LocationWithQuantityModel> ambulanceCenters;
    private List<LocationWithQuantityModel> fireTrucks;
    private List<LocationWithQuantityModel> policeCars;

    public State() {
        requests = new ArrayList<>();
        ambulanceCenters = new ArrayList<>();
        fireTrucks = new ArrayList<>();
        policeCars = new ArrayList<>();
    }

    public List<CallsNextResponseModel> getRequests() {
        return requests;
    }

    public List<LocationWithQuantityModel> getAmbulanceCenters() {
        return ambulanceCenters;
    }

    public List<LocationWithQuantityModel> getFireTrucks() {
        return fireTrucks;
    }

    public List<LocationWithQuantityModel> getPoliceCars() {
        return policeCars;
    }
}
