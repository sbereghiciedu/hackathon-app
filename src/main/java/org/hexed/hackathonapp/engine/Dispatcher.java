package org.hexed.hackathonapp.engine;

import org.hexed.hackathonapp.model.api.medical.DispatchModel;

import java.util.List;

public interface Dispatcher {

    public List<DispatchModel> dispatch(State state);
}
