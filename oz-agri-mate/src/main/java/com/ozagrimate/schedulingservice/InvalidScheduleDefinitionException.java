package com.ozagrimate.schedulingservice;

public class InvalidScheduleDefinitionException extends RuntimeException {
    public InvalidScheduleDefinitionException(String message) {
        super(message);
    }
}
