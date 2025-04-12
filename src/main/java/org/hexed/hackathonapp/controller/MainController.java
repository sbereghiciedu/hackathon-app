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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
public class MainController {
    Logger logger = LoggerFactory.getLogger(DummyController.class);

    private final ExternalApiService api;

    public MainController(ExternalApiService api) {
        this.api = api;
    }

    @GetMapping
    public String test(@RequestParam(value = "seed") String seed,
                       @RequestParam(value = "targetDispatches") int targetDispatches,
                       @RequestParam(value = "maxActiveCalls") int maxActiveCalls,
                       @RequestParam(value = "serverVersion") int version
                       ) {
        api.resetServer(new ResetParamsModel(seed, targetDispatches, maxActiveCalls), version);

        Simulator simulator = new Simulator(api, new DummyDispatcher(), logger);
        simulator.run();

        logger.info("Stopping server");
        ControlResponseModel status = api.postControlStop();
        logger.info(status.toString());

        return status.toJson();
    }
}
