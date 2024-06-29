package com.matrix.sportopia.exceptions.handle;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Object object) {
        super(object.getClass().getName() + " not found");
    }
}
