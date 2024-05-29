package com.matrix.sportopia.controllers;

import com.matrix.sportopia.entities.dto.request.StadiumRequestDto;
import com.matrix.sportopia.entities.dto.response.StadiumResponseDto;
import com.matrix.sportopia.services.StadiumService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/api/stadiums")
public class StadiumController {
    private final StadiumService stadiumService;

    @GetMapping("/{id}")
    public StadiumResponseDto getById(@PathVariable Long id){
        return stadiumService.getById(id);
    }

    @GetMapping
    public List<StadiumResponseDto> getAll(){
        return stadiumService.getAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public StadiumResponseDto add(@RequestBody StadiumRequestDto stadiumRequestDto){
        return stadiumService.add(stadiumRequestDto);
    }

    @PutMapping
    public StadiumResponseDto update(@RequestBody StadiumRequestDto stadiumRequestDto){
        return stadiumService.update(stadiumRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        stadiumService.delete(id);
    }
}
