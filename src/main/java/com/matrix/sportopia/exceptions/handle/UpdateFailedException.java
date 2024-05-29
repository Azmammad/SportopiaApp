package com.matrix.sportopia.exceptions.handle;

public class UpdateFailedException extends RuntimeException{
    public UpdateFailedException(String message,Throwable cause){
        super(message,cause);
    }
}
