package org.hexed.hackathonapp.controller;

import org.hexed.hackathonapp.service.DataProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debug")
public class DebugController {
    Logger logger = LoggerFactory.getLogger(DebugController.class);

    private final DataProcessingService dataProcessingService;

    public DebugController(DataProcessingService dataProcessingService) {
        this.dataProcessingService = dataProcessingService;
    }

    @GetMapping
    public String pong() {
        String pong = dataProcessingService.ping();
        logger.info(pong);
        return "{\"message\": \"%s\"}".formatted(pong);
    }
}
