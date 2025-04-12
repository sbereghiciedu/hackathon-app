package org.hexed.hackathonapp.controller;

import org.hexed.hackathonapp.engine.DummyDispatcher;
import org.hexed.hackathonapp.engine.MaxFlowMinCostDispatcher;
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
        api.resetServer(new ResetParamsModel("bellalite", 10000, 10000), 1);

        Simulator simulator = new Simulator(api, new DummyDispatcher(), logger);
        simulator.run();

        logger.info("Stopping server");
        ControlResponseModel status = api.postControlStop();
        logger.info(status.toString());

        return status.toJson();
    }

    @GetMapping(value = "/2")
    public String test2() {
        new ResetParamsModel("dummy", 100, 10);
        Simulator simulator = new Simulator(api, new MaxFlowMinCostDispatcher(), logger);
        simulator.run();

        logger.info("Stopping server");
        ControlResponseModel controlStopStatus = api.postControlStop();
        logger.info(controlStopStatus.toString());

        return controlStopStatus.toString();
    }
}
