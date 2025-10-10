package com.nastyhaze.homeworld.hwe_app.exception;

import com.nastyhaze.homeworld.hwe_app.constant.CrudOperationType;

/**
 *  Custom Exception class for CRUD operations in the Board Service.
 */
public class BoardServiceException extends RuntimeException {
    public BoardServiceException() {
        super("ERROR: Failure in the BoardService.");
    }

    public BoardServiceException(Long boardId) {
        super(String.format("ERROR: Failure interacting with Board id: %d in the BoardService.", boardId));
    }

    public BoardServiceException(CrudOperationType opType) {
        super(String.format("ERROR: Failure with Board %s operation in the BoardService.", opType));
    }

    public BoardServiceException(Long boardId, CrudOperationType opType) {
        super(String.format("ERROR: Failure with %s operation for Board: %d in the BoardService.", opType, boardId));
    }
}
