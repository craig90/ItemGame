package com.craig.itemgame.exceptions;

public class ErrorResponse {

    private String errorCode;
    private String message;

    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
    
    public String getMessage() {
        return this.message;
    }


}