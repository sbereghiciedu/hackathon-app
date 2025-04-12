package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.model.api.interventioncenter.DispatchModel;
import org.hexed.hackathonapp.model.api.interventioncenter.TypedDispatchModel;

import java.util.ArrayList;
import java.util.List;

public class DispatchStore {

    private static DispatchStore instance;

    private final List<TypedDispatchModel> dispatches;

    private DispatchStore() {
        dispatches = new ArrayList<>();
    }

    public static synchronized DispatchStore getInstance() {
        if (instance == null) {
            instance = new DispatchStore();
        }
        return instance;
    }

    public List<TypedDispatchModel> getDispatches() {
        return dispatches;
    }
}
