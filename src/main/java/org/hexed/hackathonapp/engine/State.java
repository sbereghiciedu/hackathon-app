package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.model.api.calls.RequestModel;
import org.hexed.hackathonapp.model.api.calls.RequestType;
import org.hexed.hackathonapp.model.api.interventioncenter.InterventionCenterModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State {

    private final List<RequestModel> requests;
    private final Map<RequestType, List<InterventionCenterModel>> interventionCenters;

    public State() {
        requests = new ArrayList<>();
        interventionCenters  = new HashMap<>();
        for (RequestType type : RequestType.values()) {
            interventionCenters.put(type, new ArrayList<>());
        }
    }

    public List<RequestModel> getRequests() {
        return requests;
    }

    public List<InterventionCenterModel> getInterventionCenters(RequestType type) {
        return interventionCenters.get(type);
    }
}
