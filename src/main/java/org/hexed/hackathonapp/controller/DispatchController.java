package org.hexed.hackathonapp.controller;

import org.hexed.hackathonapp.engine.DispatchStore;
import org.hexed.hackathonapp.model.api.interventioncenter.DispatchModel;
import org.hexed.hackathonapp.service.api.ExternalApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dispatches")
public class DispatchController {

    Logger logger = LoggerFactory.getLogger(DummyController.class);

    public DispatchController(ExternalApiService api) {
    }

    @GetMapping
    public List<DispatchModel> getDi(@RequestParam(defaultValue = "0") int index) {
        List<DispatchModel> dispatches = new ArrayList<>(DispatchStore.getInstance().getDispatches());
        return dispatches.subList(index, dispatches.size());
    }
}
