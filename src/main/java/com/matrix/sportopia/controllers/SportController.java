package com.matrix.sportopia.controllers;

import com.matrix.sportopia.entities.dto.request.SportRequestDto;
import com.matrix.sportopia.entities.dto.response.SportResponseDto;
import com.matrix.sportopia.services.SportService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/api/sports")
public class SportController {

    private final SportService sportService;

    @GetMapping("/{id}")
    public SportResponseDto getById(@PathVariable Long id){
        return sportService.getById(id);
    }

    @GetMapping("/sport-list")
    public List<SportResponseDto> getAll(){
        return sportService.getAll();
    }

    @GetMapping("/noActive-sport-list")
    public List<SportResponseDto> getAllNoActiveSports(@RequestBody SportRequestDto sportRequestDto){
        return sportService.getAll();
    }


    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public SportResponseDto add(@RequestBody SportRequestDto sportRequest){
        return sportService.add(sportRequest);
    }

    @PutMapping("upsert/{id}")
    public SportResponseDto update(@PathVariable Long id,@RequestBody SportRequestDto sportRequestDto){
        return sportService.update(id,sportRequestDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        sportService.delete(id);
    }
}
