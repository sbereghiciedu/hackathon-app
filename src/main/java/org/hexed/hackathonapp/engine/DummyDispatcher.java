package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.model.api.calls.RequestModel;
import org.hexed.hackathonapp.model.api.calls.RequestType;
import org.hexed.hackathonapp.model.api.interventioncenter.DispatchModel;
import org.hexed.hackathonapp.model.api.interventioncenter.InterventionCenterModel;

public class DummyDispatcher implements Dispatcher {

    @Override
    public DispatchResponse dispatch(State state, RequestType type) {
        DispatchResponse response = new DispatchResponse();

        RequestModel request = null;
        int i = 0;
        while (request == null && i < state.getRequests().size()) {
            if (state.getRequests().get(i).getRequest(type).getQuantity() > 0) {
                request = state.getRequests().get(i);
            } else {
                i++;
            }
        }
        if (request != null && !state.getInterventionCenters(type).isEmpty()) {
            InterventionCenterModel center = state.getInterventionCenters(type).getFirst();

            DispatchModel dispatch = response.dispatch(type, request, center);

            System.out.println(dispatch);
        }

        return response;
    }
}
