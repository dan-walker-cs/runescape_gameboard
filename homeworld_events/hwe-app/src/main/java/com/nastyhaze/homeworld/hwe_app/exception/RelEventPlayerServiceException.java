package com.nastyhaze.homeworld.hwe_app.exception;

import com.nastyhaze.homeworld.hwe_app.constant.CrudOperationType;

/**
 *  Custom Exception class for CRUD operations in the RelEventPlayer Service.
 */
public class RelEventPlayerServiceException extends RuntimeException {
    public RelEventPlayerServiceException() {
        super("ERROR: Failure in the RelEventPlayerService.");
    }

    public RelEventPlayerServiceException(Long eventId) {
        super(String.format("ERROR: Failure interacting with Event id: %d in the RelEventPlayerService.", eventId));
    }

    public RelEventPlayerServiceException(CrudOperationType opType) {
        super(String.format("ERROR: Failure with Event %s operation in the RelEventPlayerService.", opType));
    }

    public RelEventPlayerServiceException(Long eventId, CrudOperationType opType) {
        super(String.format("ERROR: Failure with %s operation for Event: %d in the RelEventPlayerService.", opType, eventId));
    }
}
