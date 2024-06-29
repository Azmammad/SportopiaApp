package com.matrix.sportopia.controllers;

import com.matrix.sportopia.models.dto.request.StadiumRequestDto;
import com.matrix.sportopia.models.dto.response.StadiumResponseDto;
import com.matrix.sportopia.services.StadiumService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("")
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

    @GetMapping("/sport/{sportId}")
    public List<StadiumResponseDto> getStadiumsBySportId(@PathVariable Long sportId){
        return stadiumService.getStadiumsBySportId(sportId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public StadiumResponseDto add(@RequestBody StadiumRequestDto stadiumRequestDto){
        return stadiumService.add(stadiumRequestDto);
    }

    @PutMapping("/{id}")
    public StadiumResponseDto update(@PathVariable Long id,@RequestBody StadiumRequestDto stadiumRequestDto){
        return stadiumService.update(id,stadiumRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        stadiumService.delete(id);
    }

    @PutMapping("/change-status/{id}")
    public void changeStatus(@PathVariable Long id){
        stadiumService.changeStatus(id);
    }
}
