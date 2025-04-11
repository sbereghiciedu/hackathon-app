package org.hexed.hackathonapp.controller;

import org.hexed.hackathonapp.model.api.calls.RequestModel;
import org.hexed.hackathonapp.model.api.calls.RequestType;
import org.hexed.hackathonapp.model.api.control.ControlResponseModel;
import org.hexed.hackathonapp.model.api.control.ResetParamsModel;
import org.hexed.hackathonapp.model.api.location.LocationModel;
import org.hexed.hackathonapp.model.api.interventioncenter.DispatchModel;
import org.hexed.hackathonapp.model.api.interventioncenter.InterventionCenterModel;
import org.hexed.hackathonapp.service.api.ExternalApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

@RestController
@RequestMapping("/t0")
public class T0Controller {

    Logger logger = LoggerFactory.getLogger(T0Controller.class);

    private final ExternalApiService api;

    public T0Controller(ExternalApiService api) {
        this.api = api;
    }

    @GetMapping
    public String test() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);

        ControlResponseModel controlStatus = api.getControlStatus();
        ControlResponseModel controlResetStatus = api.postControlReset(new ResetParamsModel("test", 1000, 5));

        RequestModel dest = api.getCallsNext();
        List<RequestModel> callsQueue = api.getCallsQueue();

        List<LocationModel> locations = api.getLocations();
        for(LocationModel location : locations) {
            ps.println(location);
        }

        List<InterventionCenterModel> medicalSearch = api.getInterventionCenters(RequestType.MEDICAL);

        InterventionCenterModel source = medicalSearch.getFirst();
        Integer available = api.getInterventionCentersByCity(RequestType.MEDICAL, source.getCounty(), source.getCity());

        assert available == source.getQuantity();

        String dispatch = api.postDispatch(RequestType.MEDICAL, new DispatchModel(source.getCounty(), source.getCity(),
                dest.getCounty(), dest.getCity(), 1));


        ControlResponseModel controlStopStatus = api.postControlStop();

        return new String(out.toByteArray());
    }
}
