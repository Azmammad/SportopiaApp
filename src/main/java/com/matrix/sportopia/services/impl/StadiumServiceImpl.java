package com.matrix.sportopia.services.impl;

import com.matrix.sportopia.entities.Sport;
import com.matrix.sportopia.entities.Stadium;
import com.matrix.sportopia.entities.dto.request.StadiumRequestDto;
import com.matrix.sportopia.entities.dto.response.StadiumResponseDto;
import com.matrix.sportopia.exceptions.handle.AlreadyExistException;
import com.matrix.sportopia.exceptions.handle.EntityNotFoundException;
import com.matrix.sportopia.mapper.StadiumMapper;
import com.matrix.sportopia.repositories.SportRepository;
import com.matrix.sportopia.repositories.StadiumRepository;
import com.matrix.sportopia.services.StadiumService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Data
@Slf4j
public class StadiumServiceImpl implements StadiumService {

    private final StadiumRepository stadiumRepository;
    private final SportRepository sportRepository;
    private final StadiumMapper stadiumMapper;

    @Override
    public StadiumResponseDto getById(Long id) {
        log.info("stadium-service get by id operation with id = {}", id);
        StadiumResponseDto stadiumResponseDto = stadiumRepository.findById(id).map(stadiumMapper::toResponse).
                orElseThrow(()->new EntityNotFoundException("stadium not found"));
        log.info("-> Successful! stadium-service get by id operation with id = {}", id);
        return stadiumResponseDto;
    }

    @Override
    public List<StadiumResponseDto> getAll() {
        log.info("-> started getAll method for stadium");
        List<StadiumResponseDto> list = stadiumRepository.findAll().stream().
                map(stadiumMapper::toResponse).collect(Collectors.toList());
        if (list.isEmpty()){
            throw new EntityNotFoundException("Stadiums not found");
        }
        log.info("-> Successfully");
        return list;
    }

    @Override
    public StadiumResponseDto add(StadiumRequestDto stadiumRequestDto) {
        Stadium stadium = stadiumMapper.toEntity(stadiumRequestDto);
        Sport sport = sportRepository.findById(stadiumRequestDto.getSportId())
                .orElseThrow(() -> new AlreadyExistException("Sport not found with id: " + stadiumRequestDto.getSportId()));
        stadium.setSport(sport);
        return stadiumMapper.toResponse(stadiumRepository.save(stadium));
    }

    @Override
    public StadiumResponseDto update(StadiumRequestDto stadiumRequestDto) {
        Stadium stadium = stadiumMapper.toEntity(stadiumRequestDto);
        return stadiumMapper.toResponse(stadiumRepository.save(stadium));
    }

    @Override
    public void delete(Long id) {
        if (stadiumRepository.existsById(id)) {
            stadiumRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Stadium with id " + id + " not found");
        }
    }
}
