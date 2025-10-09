package com.nastyhaze.homeworld.hwe_app.exception;

import com.nastyhaze.homeworld.hwe_app.constant.CrudOperationType;

/**
 *  Custom Exception class for CRUD operations in the Player Service.
 */
public class PlayerServiceException extends RuntimeException {

    public PlayerServiceException() {
        super("ERROR: Failure in the PlayerService.");
    }

    public PlayerServiceException(String displayName) {
        super(String.format("ERROR: Failure interacting with Player display_name: %s in the PlayerService.", displayName));
    }

    public PlayerServiceException(CrudOperationType opType) {
        super(String.format("ERROR: Failure with Player %s operation in the PlayerService.", opType));
    }

    public PlayerServiceException(String displayName, CrudOperationType opType) {
        super(String.format("ERROR: Failure with %s operation for Player: %s in the PlayerService.", opType, displayName));
    }
}
