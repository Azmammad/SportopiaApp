package com.matrix.sportopia.entities.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationRequestDto {
    private Long userId;
    private Long stadiumId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @NotNull
    private Boolean status;
    /*@OneToOne(mappedBy = "reservation")
    private Payment payment;*/
}
