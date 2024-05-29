package com.matrix.sportopia.entities.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationResponseDto {

    private Long userId;
    private Long stadiumId;
    private LocalDateTime startTime;
    private Boolean status;

}
