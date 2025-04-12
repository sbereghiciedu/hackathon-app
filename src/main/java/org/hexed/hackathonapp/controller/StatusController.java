package org.hexed.hackathonapp.controller;

import org.hexed.hackathonapp.model.api.control.ControlResponseModel;
import org.hexed.hackathonapp.model.api.location.LocationModel;
import org.hexed.hackathonapp.service.api.ExternalApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/status")
public class StatusController {

    Logger logger = LoggerFactory.getLogger(DummyController.class);

    private final ExternalApiService api;

    public StatusController(ExternalApiService api) {
        this.api = api;
    }

    @GetMapping
    public ControlResponseModel getStatus() {
        return api.getControlStatus();
    }
}
