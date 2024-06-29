package com.matrix.sportopia.models.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SportRequestDto {
    private String name;

    @NotNull
    private Integer status;
}
