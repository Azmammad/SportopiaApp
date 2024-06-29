package com.matrix.sportopia.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse{
    private String message;
    private Integer code;
    private Object data;
}
