package com.matrix.sportopia.entities.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StadiumResponseDto {

    private String name;
    private String address;
    private Integer price;
    private Boolean isDeleted;
}
