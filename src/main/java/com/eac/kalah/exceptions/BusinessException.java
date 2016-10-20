package com.eac.kalah.exceptions;

public class BusinessException extends KalahException {

    public BusinessException(String message) {
        super(message);
    }

    public int getHttpErrorCode() {
        return 400;
    }
}