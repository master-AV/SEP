package com.sep.id.exception;


public class EntityNotFoundException extends AppException {

    public EntityNotFoundException() {
        super("Entity is not found");
    }

}
