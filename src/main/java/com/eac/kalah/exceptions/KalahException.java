package com.eac.kalah.exceptions;

public class KalahException extends RuntimeException {
    private static final long serialVersionUID = -6299175182809658885L;

    public KalahException(String message) {
        super(message);
    }

    public KalahException(String message, Throwable cause) {
        super(message, cause);
    }

    public KalahException(Throwable cause) {
        super(cause);
    }

    public KalahException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}