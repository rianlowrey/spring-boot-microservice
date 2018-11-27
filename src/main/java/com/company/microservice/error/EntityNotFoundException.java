package com.company.microservice.error;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String exception) {
        super(exception);
    }

}