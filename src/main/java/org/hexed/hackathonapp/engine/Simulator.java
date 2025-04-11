package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.model.api.calls.CallsNextResponseModel;
import org.hexed.hackathonapp.model.api.exceptions.CallLimitException;
import org.hexed.hackathonapp.model.api.exceptions.NoMoreCallsException;
import org.hexed.hackathonapp.model.api.medical.DispatchModel;
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
        State state = new State();

        state.getAmbulanceCenters().addAll(api.getMedicalSearch());

        boolean stillPlaying = true;
        while (stillPlaying) {
            CallsNextResponseModel req;
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

            List<DispatchModel> dispatches = dispatcher.dispatch(state);
            if (dispatches.isEmpty()) {
                stillPlaying = false;
            }

            for (DispatchModel dispatch : dispatches) {
                api.postMedicalDispatch(dispatch);
            }
        }
    }
}
