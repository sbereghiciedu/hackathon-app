package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.model.api.calls.CallsNextResponseModel;
import org.hexed.hackathonapp.model.api.medical.DispatchModel;
import org.hexed.hackathonapp.model.api.medical.LocationWithQuantityModel;

import java.util.ArrayList;
import java.util.List;

public class DummyDispatcher implements Dispatcher {

    @Override
    public List<DispatchModel> dispatch(State state) {
        List<DispatchModel> dispatches = new ArrayList<>();
        if (!state.getRequests().isEmpty()) {
            CallsNextResponseModel request = state.getRequests().get(0);

            if (!state.getAmbulanceCenters().isEmpty()) {
                LocationWithQuantityModel ambulanceCenter = state.getAmbulanceCenters().get(0);

                DispatchModel dispatch = new DispatchModel();
                dispatch.setSourceCounty(ambulanceCenter.getCounty());
                dispatch.setSourceCity(ambulanceCenter.getCity());
                dispatch.setTargetCounty(request.getCounty());
                dispatch.setTargetCity(request.getCity());

                int q = Math.min(request.getRequests().get(0).getQuantity(), ambulanceCenter.getQuantity());
                dispatch.setQuantity(q);
                dispatches.add(dispatch);
                System.out.println(dispatch);

                request.getRequests().get(0).setQuantity(request.getRequests().get(0).getQuantity() - q);
                if (request.getRequests().get(0).getQuantity() == 0) {
                    state.getRequests().remove(request);
                }

                ambulanceCenter.setQuantity(ambulanceCenter.getQuantity() - q);
                if (ambulanceCenter.getQuantity() == 0) {
                    state.getAmbulanceCenters().remove(ambulanceCenter);
                }
            }
        }

        return dispatches;
    }
}
