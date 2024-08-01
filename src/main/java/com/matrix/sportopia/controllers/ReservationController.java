package com.matrix.sportopia.controllers;

import com.matrix.sportopia.models.dto.request.ReservationRequestDto;
import com.matrix.sportopia.models.dto.response.ReservationResponseDto;
import com.matrix.sportopia.services.ReservationService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@Data
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/{id}")
    public ReservationResponseDto getById(@PathVariable Long id){
        return reservationService.getById(id);
    }

    @GetMapping
    public List<ReservationResponseDto> getAll(){
        return reservationService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponseDto add(@RequestBody ReservationRequestDto reservationRequestDto){
        return reservationService.add(reservationRequestDto);
    }

    @PutMapping
    public ReservationResponseDto update(@PathVariable Long id,@RequestBody ReservationRequestDto reservationRequestDto){
        return reservationService.update(id,reservationRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
    reservationService.delete(id);
    }
}
