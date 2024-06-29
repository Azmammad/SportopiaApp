package com.matrix.sportopia.exceptions.handle;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomException extends RuntimeException {
    private Integer code;
    private Object data;

    public CustomException(String message, int code, Object data) {
        super(message);
        this.code = code;
        this.data = message;
    }

    public CustomException(String message, int code, Object data, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.data = cause.getMessage();
    }
}
