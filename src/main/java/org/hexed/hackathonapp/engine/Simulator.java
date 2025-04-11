package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.model.api.calls.RequestModel;
import org.hexed.hackathonapp.model.api.exceptions.CallLimitException;
import org.hexed.hackathonapp.model.api.exceptions.NoMoreCallsException;
import org.hexed.hackathonapp.model.api.medical.DispatchModel;
import org.hexed.hackathonapp.model.api.medical.InterventionCenterModel;
import org.hexed.hackathonapp.service.api.ExternalApiService;

import java.util.List;

public class Simulator implements Runnable {

    private ExternalApiService api;
    private Dispatcher dispatcher;

    public Simulator(ExternalApiService api, Dispatcher dispatcher) {
        this.api = api;
        this.dispatcher = dispatcher;
    }

    public void run() {
    }

    public void run(String type) {
        State state = new State();

        state.getAmbulanceCenters().addAll(api.getMedicalSearch());

        boolean stillPlaying = true;
        while (stillPlaying) {
            RequestModel req;
            do {
                req = null;
                try {
                    req = api.getCallsNext();
                    state.getRequests().add(req);
                } catch (CallLimitException e) {
                    // nothing to do, exit the loop smoothly
                } catch (NoMoreCallsException e) {
                    stillPlaying = false;
                }

            } while (req != null);

            Dispatcher.DispatchResponse response = dispatcher.dispatch(state);

            if (response.getDispatches().isEmpty()) {
                stillPlaying = false;
            } else for (int i = 0; i < response.getDispatches().size(); i++) {
                DispatchModel dispatch = response.getDispatches().get(0);
                RequestModel request = response.getRequests().get(i);
                InterventionCenterModel center = response.getCenters().get(i);

                request.getRequests().get(0).setQuantity(request.getRequests().get(0).getQuantity() - dispatch.getQuantity());
                if (request.getRequests().get(0).getQuantity() == 0) {
                    state.getRequests().remove(request);
                }

                center.setQuantity(center.getQuantity() - dispatch.getQuantity());
                if (center.getQuantity() == 0) {
                    state.getAmbulanceCenters().remove(center);
                }

                api.postMedicalDispatch(dispatch);
            }

        }
    }
}
