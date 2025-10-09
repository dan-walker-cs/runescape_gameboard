package com.nastyhaze.homeworld.hwe_app.exception;

import com.nastyhaze.homeworld.hwe_app.constant.CrudOperationType;

/**
 *  Custom Exception class for CRUD operations in the Tile Service.
 */
public class TileServiceException extends RuntimeException {

    public TileServiceException() {
        super("ERROR: Failure in the TileService.");
    }

    public TileServiceException(Long tileId) {
        super(String.format("ERROR: Failure interacting with Tile id: %d in the TileService.", tileId));
    }

    public TileServiceException(CrudOperationType opType) {
        super(String.format("ERROR: Failure with Tile %s operation in the TileService.", opType));
    }

    public TileServiceException(Long tileId, CrudOperationType opType) {
        super(String.format("ERROR: Failure with %s operation for Tile: %d in the TileService.", opType, tileId));
    }
}
