package com.sep.id.exception;

import org.hibernate.type.EntityType;

public class AppException extends Exception{

    private final String message;

    public AppException(String id, EntityType entityType) {
        super();
        this.message = "Exception";// createExceptionMessage(id, entityType);
    }

    public AppException(String message) {
        super();
        this.message = message;
    }

//    private String createExceptionMessage(String id, EntityType entityType) {
//        return getEntityErrorMessage(id, entityType);
//    }

    @Override
    public String getMessage() {
        return message;
    }
}
