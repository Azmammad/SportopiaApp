package com.matrix.sportopia.models.dto.request;

import com.matrix.sportopia.enums.StadiumStatus;
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
    private StadiumStatus status;
}
