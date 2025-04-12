package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.controller.DebugController;
import org.hexed.hackathonapp.math.InputEdge;
import org.hexed.hackathonapp.math.MinCostMaxFlow;
import org.hexed.hackathonapp.model.api.calls.RequestType;
import org.hexed.hackathonapp.model.api.interventioncenter.DispatchModel;
import org.hexed.hackathonapp.model.api.interventioncenter.InterventionCenterModel;
import org.hexed.hackathonapp.model.api.location.LocationModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MaxFlowMinCostDispatcher implements Dispatcher {
    Logger logger = LoggerFactory.getLogger(MaxFlowMinCostDispatcher.class);


    @Override
    public DispatchResponse dispatch(State state, RequestType type) {
        DispatchResponse response = new DispatchResponse();

        List<InputEdge> edges = new ArrayList<>();

        List<InterventionCenterModel> sourceNodes = state.getInterventionCenters().get(type);
        List<State.Request> destinationNodes = state.getRequests().get(type);

        List<LocationModel> locations = state.getLocations();

        // id from 1 to sourceNodes.size()
        int toId = sourceNodes.size() + 1;
        for (State.Request destNode : destinationNodes) {
            int capacity = destNode.getQ();
            int i = 0;
            boolean foundI = false;
            for (; i < locations.size(); i++) {
                LocationModel location = locations.get(i);
                if (location.getName().equals(destNode.getCity()) && location.getCounty().equals(destNode.getCounty())) {
                    foundI = true;
                    break;
                }
            }

            for (int fromId = 1; fromId <= sourceNodes.size(); fromId++) {
                InterventionCenterModel sourceNode = sourceNodes.get(fromId - 1);

                int j = 0;
                boolean foundJ = false;
                for (; j < locations.size(); j++) {
                    LocationModel location = locations.get(j);
                    if (location.getName().equals(sourceNode.getCity()) && location.getCounty().equals(sourceNode.getCounty())) {
                        foundJ = true;
                        break;
                    }
                }

                if (!foundI || !foundJ) {
                    int k = 1;
                }

                int cost = state.getDistances()[i][j];
                edges.add(new InputEdge(fromId, toId, capacity, cost));
            }
            ++toId;
        }

        // make fake source edges
        for (int i = 1; i <= sourceNodes.size(); i++) {
            InterventionCenterModel sourceNode = sourceNodes.get(i - 1);
            edges.add(new InputEdge(0, i, sourceNode.getQuantity(), 0));
        }

        int maxNodeId = sourceNodes.size() + destinationNodes.size() + 1;
        // make fake dest edges
        for (int i = 1; i <= destinationNodes.size(); i++) {
            State.Request request = destinationNodes.get(i - 1);
            edges.add(new InputEdge(sourceNodes.size() + i, maxNodeId, request.getQ(), 0));
        }


        List<List<Integer>> solution = MinCostMaxFlow.run(sourceNodes.size() + destinationNodes.size() + 2, edges);

        if (solution.isEmpty()) {
            return response;
        }

        solution.sort((a, b) -> b.get(2) - a.get(2));

        for (List<Integer> ints : solution) {
            int sourceId = ints.get(0);
            InterventionCenterModel center = sourceNodes.get(sourceId - 1);
            int destId = ints.get(1);
            int quantity = ints.get(2);
            State.Request destination = destinationNodes.get(destId - sourceNodes.size() - 1);

            response.dispatch(destination, center, quantity);
            logger.warn("Has Available: %d, sent %d".formatted(center.getQuantity(), quantity));
        }
        return response;
    }
}
