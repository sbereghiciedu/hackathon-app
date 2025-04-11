package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.model.api.calls.CallsNextResponseModel;
import org.hexed.hackathonapp.service.api.ExternalApiService;

public class Simulator implements Runnable {

    private ExternalApiService api;
    private Dispatcher dispatcher;

    public Simulator(ExternalApiService api, Dispatcher dispatcher) {
        this.api = api;
        this.dispatcher = dispatcher;
    }

    public void run() {
        State state = new State();

        CallsNextResponseModel req = api.getCallsNext();
        state.getRequests().add(req);

    }
}
