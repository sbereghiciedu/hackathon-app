package org.hexed.hackathonapp.controller;

import org.hexed.hackathonapp.engine.DummyDispatcher;
import org.hexed.hackathonapp.engine.Simulator;
import org.hexed.hackathonapp.model.api.control.ControlResponseModel;
import org.hexed.hackathonapp.model.api.control.ResetParamsModel;
import org.hexed.hackathonapp.service.api.ExternalApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dummy")
public class DummyController {
    Logger logger = LoggerFactory.getLogger(DummyController.class);

    private final ExternalApiService api;

    public DummyController(ExternalApiService api) {
        this.api = api;
    }

    @GetMapping
    public String test() {
        ControlResponseModel controlStatus = api.getControlStatus();
        ControlResponseModel controlResetStatus = api.postControlReset(new ResetParamsModel("dummy", 100, 5));

        Simulator simulator = new Simulator(api, new DummyDispatcher());
        simulator.run();

        return "Done!";
    }
}
