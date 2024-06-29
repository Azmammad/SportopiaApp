package com.matrix.sportopia.exceptions;

import com.matrix.sportopia.exceptions.handle.*;
import com.matrix.sportopia.models.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<?> handleAlreadyExistException(AlreadyExistException e){
        log.error("AlreadyExistException: {}", e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e){
        log.error("EntityNotFoundException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 500, "Melumat tapilmadi");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }



    @ExceptionHandler(UpdateFailedException.class)
    public ResponseEntity<?> handleUpdateFailedException(UpdateFailedException e){
        log.error("-> UpdateFailedException: {}", e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<?> handleIncorrectPasswordException(IncorrectPasswordException e){
        log.error("IncorrectPasswordException: {}", e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(FileIOException.class)
    public ResponseEntity<?> handleFileIOException(FileIOException e){
        log.error("FileIOException: {}", e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(),e.getCode(),e.getData());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), 500, null);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
