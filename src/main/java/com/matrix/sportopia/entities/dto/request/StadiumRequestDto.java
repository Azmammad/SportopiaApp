package com.matrix.sportopia.entities.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StadiumRequestDto {
    private String name;
    private String city;
    private String address;
    private Integer price;
    private Long sportId;

    @NotNull
    private Boolean status;
}
