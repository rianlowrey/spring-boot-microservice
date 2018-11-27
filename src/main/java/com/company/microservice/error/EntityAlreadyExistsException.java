package com.company.microservice.error;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String exception) {
        super(exception);
    }

}