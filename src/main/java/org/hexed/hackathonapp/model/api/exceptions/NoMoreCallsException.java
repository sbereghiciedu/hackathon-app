package org.hexed.hackathonapp.model.api.exceptions;

public class NoMoreCallsException extends RuntimeException {
    public NoMoreCallsException(String message) {
        super(message);
    }
}
