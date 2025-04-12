package org.hexed.hackathonapp.controller;

import org.hexed.hackathonapp.model.api.location.LocationModel;
import org.hexed.hackathonapp.service.api.ExternalApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    Logger logger = LoggerFactory.getLogger(DummyController.class);

    private final ExternalApiService api;

    public LocationController(ExternalApiService api) {
        this.api = api;
    }

    @GetMapping
    public List<LocationModel> getLocations() {
        return api.getLocations();
    }
}
