package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.model.api.calls.RequestModel;
import org.hexed.hackathonapp.model.api.medical.DispatchModel;
import org.hexed.hackathonapp.model.api.medical.InterventionCenterModel;

import java.util.ArrayList;
import java.util.List;

public interface Dispatcher {

    public DispatchResponse dispatch(State state);

    public static class DispatchResponse {
        private List<DispatchModel> dispatches;
        private List<RequestModel> requests;
        private List<InterventionCenterModel> centers;

        public DispatchResponse() {
            dispatches = new ArrayList<>();
            requests = new ArrayList<>();
            centers = new ArrayList<>();
        }

        public DispatchModel dispatch(RequestModel request, InterventionCenterModel center) {
            DispatchModel dispatch = new DispatchModel();

            dispatch.setSourceCounty(center.getCounty());
            dispatch.setSourceCity(center.getCity());
            dispatch.setTargetCounty(request.getCounty());
            dispatch.setTargetCity(request.getCity());

            int q = Math.min(request.getRequests().get(0).getQuantity(), center.getQuantity());
            dispatch.setQuantity(q);

            dispatches.add(dispatch);
            requests.add(request);
            centers.add(center);

            return dispatch;
        }

        public List<DispatchModel> getDispatches() {
            return dispatches;
        }

        public void setDispatches(List<DispatchModel> dispatches) {
            this.dispatches = dispatches;
        }

        public List<RequestModel> getRequests() {
            return requests;
        }

        public void setRequests(List<RequestModel> requests) {
            this.requests = requests;
        }

        public List<InterventionCenterModel> getCenters() {
            return centers;
        }

        public void setCenters(List<InterventionCenterModel> centers) {
            this.centers = centers;
        }
    }
}
