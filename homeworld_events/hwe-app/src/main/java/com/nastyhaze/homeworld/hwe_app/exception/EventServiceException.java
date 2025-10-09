package com.nastyhaze.homeworld.hwe_app.exception;

import com.nastyhaze.homeworld.hwe_app.constant.CrudOperationType;

/**
 *  Custom Exception class for CRUD operations in the Event Service.
 */
public class EventServiceException extends RuntimeException {
    public EventServiceException() {
        super("ERROR: Failure in the EventService.");
    }

    public EventServiceException(Long eventId) {
        super(String.format("ERROR: Failure interacting with Event id: %d in the EventService.", eventId));
    }

    public EventServiceException(CrudOperationType opType) {
        super(String.format("ERROR: Failure with Event %s operation in the EventService.", opType));
    }

    public EventServiceException(Long eventId, CrudOperationType opType) {
        super(String.format("ERROR: Failure with %s operation for Event: %d in the EventService.", opType, eventId));
    }
}
