package org.hexed.hackathonapp.model.api.calls;

public enum RequestType {

    MEDICAL("medical"),
    FIRE("fire"),
    POLICE("police"),
    RESCUE("rescue"),
    UTILITY("utility");

    private final String key;

    public String getKey() {
        return key;
    }

    RequestType(String key) {
        this.key = key;
    }

    }
