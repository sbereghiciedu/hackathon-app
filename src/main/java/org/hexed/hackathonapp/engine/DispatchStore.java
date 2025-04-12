package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.model.api.interventioncenter.DispatchModel;
import org.hexed.hackathonapp.model.api.location.LocationModel;

import java.util.ArrayList;
import java.util.List;

public class DispatchStore {

    private static DispatchStore instance;

    private final List<DispatchModel> dispatches;

    private DispatchStore() {
        dispatches = new ArrayList<>();
    }

    public static synchronized DispatchStore getInstance() {
        if (instance == null) {
            instance = new DispatchStore();
        }
        return instance;
    }

    public List<DispatchModel> getDispatches() {
        return dispatches;
    }
}
