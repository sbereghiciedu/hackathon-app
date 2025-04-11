package org.hexed.hackathonapp.model.api.exceptions;

public class CallLimitException extends RuntimeException {

    public CallLimitException(String message) {
        super(message);
    }
}
