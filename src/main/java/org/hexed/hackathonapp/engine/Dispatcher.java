package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.model.api.calls.RequestType;
import org.hexed.hackathonapp.model.api.interventioncenter.DispatchModel;
import org.hexed.hackathonapp.model.api.interventioncenter.InterventionCenterModel;

import java.util.ArrayList;
import java.util.List;

public interface Dispatcher {

    DispatchResponse dispatch(State state, RequestType type);

    class DispatchResponse {
        private final List<DispatchModel> dispatches;
        private final List<State.Request> requests;
        private final List<InterventionCenterModel> centers;

        public DispatchResponse() {
            dispatches = new ArrayList<>();
            requests = new ArrayList<>();
            centers = new ArrayList<>();
        }

        public DispatchModel dispatch(State.Request request, InterventionCenterModel center) {
            DispatchModel dispatch = new DispatchModel();

            dispatch.setSourceCounty(center.getCounty());
            dispatch.setSourceCity(center.getCity());
            dispatch.setTargetCounty(request.getCounty());
            dispatch.setTargetCity(request.getCity());

            int q = Math.min(request.getQ(), center.getQuantity());
            dispatch.setQuantity(q);

            dispatches.add(dispatch);
            requests.add(request);
            centers.add(center);

            return dispatch;
        }

        public DispatchModel dispatch(State.Request request, InterventionCenterModel center, int q) {
            DispatchModel dispatch = new DispatchModel();

            dispatch.setSourceCounty(center.getCounty());
            dispatch.setSourceCity(center.getCity());
            dispatch.setTargetCounty(request.getCounty());
            dispatch.setTargetCity(request.getCity());

            dispatch.setQuantity(q);

            dispatches.add(dispatch);
            requests.add(request);
            centers.add(center);

            return dispatch;
        }

        public List<DispatchModel> getDispatches() {
            return dispatches;
        }

        public List<State.Request> getRequests() {
            return requests;
        }

        public List<InterventionCenterModel> getCenters() {
            return centers;
        }
    }
}
