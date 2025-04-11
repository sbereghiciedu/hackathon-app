package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.model.api.calls.RequestDetail;
import org.hexed.hackathonapp.model.api.calls.RequestModel;
import org.hexed.hackathonapp.model.api.calls.RequestType;
import org.hexed.hackathonapp.model.api.exceptions.CallLimitException;
import org.hexed.hackathonapp.model.api.exceptions.NoMoreCallsException;
import org.hexed.hackathonapp.model.api.interventioncenter.DispatchModel;
import org.hexed.hackathonapp.model.api.interventioncenter.InterventionCenterModel;
import org.hexed.hackathonapp.service.api.ExternalApiService;

public class Simulator implements Runnable {

    private final ExternalApiService api;
    private final Dispatcher dispatcher;

    public Simulator(ExternalApiService api, Dispatcher dispatcher) {
        this.api = api;
        this.dispatcher = dispatcher;
    }

    public void run() {
        run(RequestType.MEDICAL);
    }

    public void run(RequestType type) {
        State state = new State();

        state.getInterventionCenters(type).addAll(api.getInterventionCenters(type));

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

            Dispatcher.DispatchResponse response = dispatcher.dispatch(state, type);

            if (response.getDispatches().isEmpty()) {
                stillPlaying = false;
            } else for (int i = 0; i < response.getDispatches().size(); i++) {
                DispatchModel dispatch = response.getDispatches().get(i);
                RequestModel request = response.getRequests().get(i);
                InterventionCenterModel center = response.getCenters().get(i);

                RequestDetail requestDetail = request.getRequest(type);
                requestDetail.setQuantity(requestDetail.getQuantity() - dispatch.getQuantity());
                // TODO check this, when to remove request
//                if (request.getRequests().get(0).getQuantity() == 0) {
//                    state.getRequests().remove(request);
//                }

                center.setQuantity(center.getQuantity() - dispatch.getQuantity());
                if (center.getQuantity() == 0) {
                    state.getInterventionCenters(type).remove(center);
                }

                api.postDispatch(type, dispatch);
            }

        }
    }
}
