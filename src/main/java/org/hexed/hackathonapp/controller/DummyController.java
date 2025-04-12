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
import org.springframework.web.bind.annotation.RequestParam;
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
    public String test(@RequestParam(defaultValue = "1") int rp, @RequestParam(defaultValue = "1") int version) {
        /*
        level: 1/2/3, seed: "revolutionrace", targetDispatches: 10000, maxActiveCalls: 100
        level: 1/2/3, seed "jollyroom", targetDispatches: 100000, maxActiveCalls: 1000
        level: 1/2/3, seed: "jaktia", targetDispatches: 10000, maxActiveCalls: 3
        level: 1/2/3, seed: "bellalite", targetDispatches: 10000, maxActiveCalls: 10000
        level 4/5, seed: "gudrun", targetDispatches: 25, maxActiveCalls: 5
        */

        ResetParamsModel requestParam = switch (rp) {
            case 1 -> new ResetParamsModel("revolutionrace", 10000, 100);
            case 2 -> new ResetParamsModel("jollyroom", 10000, 1000);
            case 3 -> new ResetParamsModel("jaktia", 10000, 3);
            case 4 -> new ResetParamsModel("bellalite", 10000, 10000);
            case 5 -> new ResetParamsModel("gudrun", 25, 5);
            default -> null;
        };
        api.resetServer(requestParam, version);

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
