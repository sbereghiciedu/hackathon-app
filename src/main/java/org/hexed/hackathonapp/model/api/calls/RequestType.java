package org.hexed.hackathonapp.model.api.calls;

import java.util.ArrayList;
import java.util.List;

public enum RequestType {

    MEDICAL("medical"),
    FIRE("fire"),
    POLICE("police"),
    RESCUE("rescue"),
    UTILITY("utility");

    private final String key;

    public static RequestType[] values(int stage) {
        return switch (stage) {
            case 1 -> new RequestType[]{MEDICAL};
            case 2 -> new RequestType[]{MEDICAL, POLICE, FIRE};
            default -> values();
        };
    }

    public String getKey() {
        return key;
    }

    RequestType(String key) {
        this.key = key;
    }
}
