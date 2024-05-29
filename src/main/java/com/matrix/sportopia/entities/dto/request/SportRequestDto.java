package com.matrix.sportopia.entities.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SportRequestDto {
    private String name;

    @NotNull
    private Boolean status;
}
