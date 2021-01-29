package com.vanderkast.smsforwardapp.helper.handling;

public class ErrorResult extends Result {
    private final Exception exception;

    public ErrorResult(int message, Exception exception) {
        super(Type.FAILURE, message);
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
