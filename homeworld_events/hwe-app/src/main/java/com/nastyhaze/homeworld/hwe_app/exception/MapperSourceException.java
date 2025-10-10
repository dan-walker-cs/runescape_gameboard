package com.nastyhaze.homeworld.hwe_app.exception;

/**
 *  Custom Exception class for CRUD operations in the Event Service.
 */
public class MapperSourceException extends RuntimeException {

    public MapperSourceException() {
        super("ERROR: Null or Empty source Entity for some Mapper class.");
    }

    public MapperSourceException(String mapperClassName) {
        super(String.format("ERROR: Null or Empty source Entity for Mapper: %s", mapperClassName));
    }
}
