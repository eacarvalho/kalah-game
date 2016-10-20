package com.eac.kalah.exceptions;

public class ResourceNotFoundException extends KalahException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public int getHttpErrorCode() {
        return 404;
    }
}