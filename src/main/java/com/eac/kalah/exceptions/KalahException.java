package com.eac.kalah.exceptions;

public class KalahException extends RuntimeException {
    private static final long serialVersionUID = -6299175182809658885L;

    public KalahException(String message) {
        super(message);
    }
}