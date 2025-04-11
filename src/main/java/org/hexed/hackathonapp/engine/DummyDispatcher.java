package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.model.api.calls.RequestModel;
import org.hexed.hackathonapp.model.api.medical.DispatchModel;
import org.hexed.hackathonapp.model.api.medical.InterventionCenterModel;

import java.util.ArrayList;
import java.util.List;

public class DummyDispatcher implements Dispatcher {

    @Override
    public DispatchResponse dispatch(State state) {
        DispatchResponse response = new DispatchResponse();

        if (!state.getRequests().isEmpty()) {
            RequestModel request = state.getRequests().get(0);

            if (!state.getAmbulanceCenters().isEmpty()) {
                InterventionCenterModel center = state.getAmbulanceCenters().get(0);

                DispatchModel dispatch = response.dispatch(request, center);

                System.out.println(dispatch);
            }
        }

        return response;
    }
}
