package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.model.api.calls.RequestModel;
import org.hexed.hackathonapp.model.api.medical.InterventionCenterModel;

import java.util.ArrayList;
import java.util.List;

public class State {

    private List<RequestModel> requests;
    private List<InterventionCenterModel> ambulanceCenters;
    private List<InterventionCenterModel> fireTrucks;
    private List<InterventionCenterModel> policeCars;

    public State() {
        requests = new ArrayList<>();
        ambulanceCenters = new ArrayList<>();
        fireTrucks = new ArrayList<>();
        policeCars = new ArrayList<>();
    }

    public List<RequestModel> getRequests() {
        return requests;
    }

    public List<InterventionCenterModel> getAmbulanceCenters() {
        return ambulanceCenters;
    }

    public List<InterventionCenterModel> getFireTrucks() {
        return fireTrucks;
    }

    public List<InterventionCenterModel> getPoliceCars() {
        return policeCars;
    }
}
