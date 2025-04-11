package org.hexed.hackathonapp.controller;

import org.hexed.hackathonapp.model.api.location.LocationModel;
import org.hexed.hackathonapp.model.api.medical.LocationWithQuantityModel;
import org.hexed.hackathonapp.model.api.calls.CallsNextResponseModel;
import org.hexed.hackathonapp.model.api.control.ControlResponseModel;
import org.hexed.hackathonapp.model.api.control.ResetParamsModel;
import org.hexed.hackathonapp.model.api.medical.DispatchModel;
import org.hexed.hackathonapp.service.api.ExternalApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/debug")
public class DebugController {
    Logger logger = LoggerFactory.getLogger(DebugController.class);

    private final ExternalApiService api;

    public DebugController(ExternalApiService api) {
        this.api = api;
    }

    @GetMapping
    public String test() {
        ControlResponseModel controlStatus = api.getControlStatus();
        ControlResponseModel controlResetStatus = api.postControlReset(new ResetParamsModel("ahsjkdh", 1000, 5));

        CallsNextResponseModel dest = api.getCallsNext();
        List<CallsNextResponseModel> callsQueue = api.getCallsQueue();

        List<LocationModel> locations = api.getLocations();

        List<LocationWithQuantityModel> medicalSearch = api.getMedicalSearch();

        LocationWithQuantityModel source = medicalSearch.getFirst();
        Integer available = api.getMedicalSearchByCity(source.getCounty(), source.getCity());

        assert available == source.getQuantity();

        String dispatch = api.postMedicalDispatch(new DispatchModel(source.getCounty(), source.getCity(),
                dest.getCounty(), dest.getCity(), 1));


        ControlResponseModel controlStopStatus = api.postControlStop();
        return "{}";
    }
}
