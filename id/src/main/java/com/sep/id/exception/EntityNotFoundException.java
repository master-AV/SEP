package com.sep.id.exception;

import com.sep.id.exception.AppException;

public class EntityNotFoundException extends AppException {

    public EntityNotFoundException(String id) {
        super(id);
    }

    public EntityNotFoundException(Long id) {
        super(id.toString());
    }
}
