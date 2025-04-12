package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.model.api.calls.RequestModel;
import org.hexed.hackathonapp.model.api.calls.RequestType;
import org.hexed.hackathonapp.model.api.exceptions.CallLimitException;
import org.hexed.hackathonapp.model.api.exceptions.NoMoreCallsException;
import org.hexed.hackathonapp.model.api.interventioncenter.DispatchModel;
import org.hexed.hackathonapp.model.api.interventioncenter.InterventionCenterModel;
import org.hexed.hackathonapp.model.api.location.LocationModel;
import org.hexed.hackathonapp.service.api.ExternalApiService;
import org.slf4j.Logger;

import java.util.List;

public class Simulator implements Runnable {

    private final Logger logger;
    private final ExternalApiService api;
    private final Dispatcher dispatcher;

    public Simulator(ExternalApiService api, Dispatcher dispatcher, Logger logger) {
        this.api = api;
        this.dispatcher = dispatcher;
        this.logger = logger;
    }

    protected void populateInterventionCentersV0(State state) {
        for (RequestType type : RequestType.values()) {
            state.getInterventionCenters().get(type).addAll(api.getInterventionCenters(type));
        }
    }

    protected void populateInterventionCentersV1(State state) {
        List<LocationModel> locations = api.getLocations();
        for (LocationModel location : locations) {
            for (RequestType type : RequestType.values()) {
                int available = api.getInterventionCentersByCity(type, location.getCounty(), location.getName());
                if (available > 0) {
                    InterventionCenterModel icm = new InterventionCenterModel(location.getCounty(), location.getName(), location.getLatitude(), location.getLongitude(), available);
                    state.getInterventionCenters().get(type).add(icm);
                }
            }
        }
    }

    public void run() {
        DispatchStore.getInstance().getDispatches().clear();
        State state = new State(api.getLocations());
        populateInterventionCentersV0(state);
        List<RequestModel> requests = api.getCallsQueue();
        for (RequestModel request : requests) {
            logger.info("Loading queue...");
            logger.info(request.toString());
            state.addRequest(request);
        }

        boolean stillPlaying = true;

        logger.info("Start processing");
        while (stillPlaying) {
            RequestModel req;
            do {
                req = null;
                try {
                    req = api.getCallsNext();
                    logger.info("Loading next call...");
                    logger.info(req.toString());
                    state.addRequest(req);
                } catch (CallLimitException e) {
                    // TODO consider not requesting more calls unless we satisfied a request
                } catch (NoMoreCallsException e) {
                    // nothing to do, exit the loop smoothly
                }

            } while (req != null);

            int dispatchCount = 0;

            for (RequestType type : RequestType.values()) {
                Dispatcher.DispatchResponse response = dispatcher.dispatch(state, type);

                dispatchCount += response.getDispatches().size();

                for (int i = 0; i < response.getDispatches().size(); i++) {
                    DispatchModel dispatch = response.getDispatches().get(i);
                    State.Request request = response.getRequests().get(i);
                    InterventionCenterModel center = response.getCenters().get(i);

                    logger.info("Attempting dispatch of type {}", type.getKey());
                    logger.info(dispatch.toString());
                    center.setQuantity(api.getInterventionCentersByCity(type, dispatch.getSourceCounty(), dispatch.getSourceCity()));
                    if (center.getQuantity() >= dispatch.getQuantity()) {
                        api.postDispatch(type, dispatch);
                        DispatchStore.getInstance().getDispatches().add(dispatch);
                        request.setQ(request.getQ() - dispatch.getQuantity());
                        if (request.getQ() == 0) {
                            state.getRequests().get(type).remove(request);
                        }

                        center.setQuantity(center.getQuantity() - dispatch.getQuantity());
                        logger.info("Dispatched");
                    } else {
                        logger.info("Dispatch Failed, insufficient available resources: " + center.getQuantity());
                    }
                    if (center.getQuantity() == 0) {
                        state.getInterventionCenters().get(type).remove(center);
                    }
                }
            }

            if (dispatchCount == 0) {
                stillPlaying = false;
            }
        }
        logger.info("Processing complete");
    }
}
