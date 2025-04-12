package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.model.api.calls.RequestModel;
import org.hexed.hackathonapp.model.api.calls.RequestType;
import org.hexed.hackathonapp.model.api.interventioncenter.DispatchModel;
import org.hexed.hackathonapp.model.api.interventioncenter.InterventionCenterModel;

public class DummyDispatcher implements Dispatcher {

    @Override
    public DispatchResponse dispatch(State state, RequestType type) {
        DispatchResponse response = new DispatchResponse();

        State.Request request = null;
        int i = 0;
        while (request == null && i < state.getRequests().get(type).size()) {
            if (state.getRequests().get(type).get(i).getQ() > 0) {
                request = state.getRequests().get(type).get(i);
            } else {
                i++;
            }
        }
        if (request != null && !state.getInterventionCenters().get(type).isEmpty()) {
            InterventionCenterModel center = state.getInterventionCenters().get(type).getFirst();

            DispatchModel dispatch = response.dispatch(request, center);

            if (dispatch.getQuantity() == 0) {
                throw new RuntimeException("dispatch quantity cannot be 0!");
            }
        }

        return response;
    }
}
