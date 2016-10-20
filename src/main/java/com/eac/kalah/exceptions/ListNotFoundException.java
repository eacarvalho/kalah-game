package com.eac.kalah.exceptions;

public class ListNotFoundException extends KalahException {

    public ListNotFoundException(String message) {
        super(message);
    }

    public int getHttpErrorCode() {
        return 204;
    }
}