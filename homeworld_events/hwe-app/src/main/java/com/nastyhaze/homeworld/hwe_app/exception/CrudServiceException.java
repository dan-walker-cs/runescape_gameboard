package com.nastyhaze.homeworld.hwe_app.exception;

import com.nastyhaze.homeworld.hwe_app.constant.CrudOperationType;

/**
 *  Custom Generic Exception class for CRUD operation Services.
 */
public class CrudServiceException extends RuntimeException {

    public CrudServiceException() {
        super("ERROR: CRUD Repository issue in Service. Provide additional Exception args for better logging.");
    }

    public CrudServiceException(String crudServiceClassName) {
        super(String.format("ERROR: CRUD Repository issue in Service: %s",
            crudServiceClassName));
    }

    public CrudServiceException(String crudServiceClassName, CrudOperationType opType) {
        super(String.format("ERROR: CRUD Repository issue in Service: %s performing %s operation.",
            crudServiceClassName, opType.name()));
    }

    public CrudServiceException(String crudServiceClassName, CrudOperationType opType, Long entityId) {
        super(String.format("ERROR: CRUD Repository issue in Service: %s performing %s operation for (TYPE_NOT_PROVIDED) Entity with id: %d.",
            crudServiceClassName, opType.name(), entityId));
    }

    public CrudServiceException(String crudServiceClassName, CrudOperationType opType, String entityClassName, Long entityId) {
        super(String.format("ERROR: CRUD Repository issue in Service: %s performing %s operation for %s Entity with id: %d.",
            crudServiceClassName, opType.name(), entityClassName, entityId));
    }
}
